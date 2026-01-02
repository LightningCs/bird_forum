package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.dto.CommentDTO;
import com.bird_forum.domain.po.Comment;
import com.bird_forum.domain.query.CommentQuery;
import com.bird_forum.domain.vo.CommentVO;
import com.bird_forum.mapper.CommentMapper;
import com.bird_forum.service.ICommentLikeService;
import com.bird_forum.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
        List<CommentVO> data = commentMapper.getComments(commentQuery);

        // 当前用户是否已点赞该评论
        data.forEach(commentVO ->
                commentVO.setIsLike(iCommentLikeService.isLike(commentVO.getId(), ThreadContext.get()))
        );

        return data;
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
}
