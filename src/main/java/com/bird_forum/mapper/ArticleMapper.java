package com.bird_forum.mapper;

import com.bird_forum.domain.po.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.query.ArticleQuery;
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
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 获取文章列表
     * @param articleQuery 查询参数
     * @return 文章列表
     */
    List<Article> listArticles(ArticleQuery articleQuery);
}
