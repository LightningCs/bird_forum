package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.dto.CommentDTO;
import com.bird_forum.domain.po.Comment;
import com.bird_forum.domain.query.CommentQuery;
import com.bird_forum.domain.vo.CommentVO;
import com.bird_forum.mapper.CommentMapper;
import com.bird_forum.service.ICommentLikeService;
import com.bird_forum.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.MinioUtils;
import io.micrometer.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ICommentLikeService iCommentLikeService;

    /**
     * 添加评论
     *
     * @param commentDTO 评论DTO
     * @return 添加结果
     */
    @Override
    public Boolean addComment(CommentDTO commentDTO) {
        Comment comment = BeanUtil.copyProperties(commentDTO, Comment.class);
        System.out.println(comment);
        // 设置创建时间为当前时间
        comment.setCreateTime(LocalDateTime.now());

        return save(comment);
    }

    /**
     * 获取评论
     *
     * @param commentQuery 评论查询参数
     * @return 评论列表
     */
    @Override
    public List<CommentVO> getComments(CommentQuery commentQuery) {
        // 如果指定了rootId，说明是获取子评论，直接返回平铺列表
        if (ObjectUtil.isNotNull(commentQuery.getRootId())) {
            List<CommentVO> data = commentMapper.listComments(commentQuery);

            // 设置头像URL和点赞状态
            data.forEach(commentVO -> {
                commentVO.setAvatar(MinioUtils.getFileUrl(commentVO.getAvatar()));
                if (commentVO.getReplyAvatar() != null) {
                    commentVO.setReplyAvatar(MinioUtils.getFileUrl(commentVO.getReplyAvatar()));
                }
                commentVO.setIsLike(iCommentLikeService.isLike(commentVO.getId(), ThreadContext.get()));
            });

            return data;
        }

        // 获取所有评论（根评论和子评论）
        List<CommentVO> allComments = commentMapper.listComments(commentQuery);

        // 设置头像URL和点赞状态
        allComments.forEach(commentVO -> {
            commentVO.setAvatar(MinioUtils.getFileUrl(commentVO.getAvatar()));
            if (commentVO.getReplyAvatar() != null) {
                commentVO.setReplyAvatar(MinioUtils.getFileUrl(commentVO.getReplyAvatar()));
            }
            commentVO.setIsLike(iCommentLikeService.isLike(commentVO.getId(), ThreadContext.get()));
        });

        // 构建树形结构
        return buildCommentTree(allComments);
    }

    /**
     * 构建评论树形结构
     *
     * @param allComments 所有评论列表
     * @return 根评论列表（包含子评论）
     */
    private List<CommentVO> buildCommentTree(List<CommentVO> allComments) {
        // 使用Map存储所有评论，便于查找
        java.util.Map<Long, CommentVO> commentMap = new java.util.HashMap<>();
        allComments.forEach(comment -> commentMap.put(comment.getId(), comment));

        // 存储根评论
        List<CommentVO> rootComments = new java.util.ArrayList<>();

        // 遍历所有评论，构建树形结构
        for (CommentVO comment : allComments) {
            if (comment.getRootId() == null || comment.getRootId() == 0) {
                // 根评论
                rootComments.add(comment);
            } else {
                // 子评论，添加到对应的根评论下
                CommentVO rootComment = commentMap.get(comment.getRootId());
                if (rootComment != null) {
                    if (rootComment.getChildren() == null) {
                        rootComment.setChildren(new java.util.ArrayList<>());
                    }
                    rootComment.getChildren().add(comment);
                }
            }
        }

        return rootComments;
    }

    /**
     * 修改评论
     *
     * @param commentDTO 评论DTO
     * @return 修改结果
     */
    @Override
    public boolean update(CommentDTO commentDTO) {
        return lambdaUpdate()
                .set(Comment::getContext, commentDTO.getContext())
                .eq(Comment::getId, commentDTO.getId())
                .update();
    }

    /**
     * 增加评论点赞数量
     *
     * @param commentId 评论id
     * @param num 数量
     * @return 添加结果
     */
    @Override
    public boolean plusLikeNum(Long commentId, int num) {
        return lambdaUpdate().setSql("like_num = like_num + " + num)
                .eq(Comment::getId, commentId)
                .update();
    }
}
