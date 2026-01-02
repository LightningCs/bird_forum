package com.bird_forum.service;

import com.bird_forum.domain.dto.CommentDTO;
import com.bird_forum.domain.po.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.CommentQuery;
import com.bird_forum.domain.vo.CommentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 添加评论
     *
     * @param commentDTO 评论DTO
     * @return 添加结果
     */
    Boolean addComment(CommentDTO commentDTO);

    /**
     * 获取文章的评论
     *
     * @param commentQuery 评论查询参数
     * @return 评论列表
     */
    List<CommentVO> getComments(CommentQuery commentQuery);

    /**
     * 修改评论
     *
     * @param commentDTO 评论DTO
     * @return 修改结果
     */
    boolean update(CommentDTO commentDTO);
}
