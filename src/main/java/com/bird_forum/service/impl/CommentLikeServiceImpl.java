package com.bird_forum.service.impl;

import com.bird_forum.domain.po.CommentLike;
import com.bird_forum.mapper.CommentLikeMapper;
import com.bird_forum.service.ICommentLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements ICommentLikeService {

    /**
     * 判断用户是否已点赞该评论
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @return 是否已点赞
     */
    @Override
    public Boolean isLike(Long commentId, Long userId) {
        CommentLike one = lambdaQuery()
                .eq(CommentLike::getCommentId, commentId)
                .eq(CommentLike::getUserId, userId)
                .one();

        return BeanUtils.isNotNull(one);
    }

    /**
     * 点赞
     *
     * @param userId 用户id
     * @param commentId 评论id
     * @return 是否成功
     */
    @Override
    public boolean like(Long userId, Long commentId) {
        return save(new CommentLike(userId, commentId));
    }

    /**
     * 取消点赞
     *
     * @param userId 用户id
     * @param commentId 评论id
     * @return 是否成功
     */
    @Override
    public boolean dislike(Long userId, Long commentId) {
        return lambdaUpdate()
                .eq(CommentLike::getUserId, userId)
                .eq(CommentLike::getCommentId, commentId)
                .remove();
    }
}
