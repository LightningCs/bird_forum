package com.bird_forum.mapper;

import com.bird_forum.domain.po.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.query.CommentQuery;
import com.bird_forum.domain.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获取评论列表（包含用户信息）
     * @param commentQuery 查询参数
     * @return 评论列表
     */
    List<CommentVO> listComments(CommentQuery commentQuery);
}
