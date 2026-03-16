package com.bird_forum.mapper;

import com.bird_forum.domain.po.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.query.ArticleQuery;
import com.bird_forum.domain.vo.ArticleVO;
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
    List<ArticleVO> listArticles(ArticleQuery articleQuery);

    /**
     * 获取热门文章列表
     * @return 热门文章列表
     */
    List<ArticleVO> listHotArticleList();

    ArticleVO getById(Long id);

    /**
     * 获取用户发布的文章列表
     * @param userId 用户id
     * @return 文章列表
     */
    List<ArticleVO> listByPublisher(Long userId);

    /**
     * 获取用户收藏的文章列表
     * @param userId 用户id
     * @return 文章列表
     */
    List<ArticleVO> listCollected(Long userId);
}
