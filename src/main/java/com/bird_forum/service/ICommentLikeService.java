package com.bird_forum.service;

import com.bird_forum.domain.po.CommentLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface ICommentLikeService extends IService<CommentLike> {

    /**
     * 判断用户是否已点赞该评论
     *
     * @param commentId 评论id
     * @param userId 用户id
     * @return 是否已点赞
     */
    Boolean isLike(Long commentId, Long userId);

    /**
     * 点赞
     *
     * @param userId 用户id
     * @param commentId 评论id
     * @return 是否成功
     */
    boolean like(Long userId, Long commentId);

    /**
     * 取消点赞
     *
     * @param userId 用户id
     * @param commentId 评论id
     * @return 是否成功
     */
    boolean dislike(Long userId, Long commentId);
}
