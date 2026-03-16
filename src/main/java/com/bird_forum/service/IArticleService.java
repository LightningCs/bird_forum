package com.bird_forum.service;

import com.bird_forum.domain.dto.ArticleDTO;
import com.bird_forum.domain.po.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.ArticleQuery;
import com.bird_forum.domain.vo.ArticleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IArticleService extends IService<Article> {
    /**
     * 获取文章列表
     * @param articleQuery 查询参数
     * @return 文章列表
     */
    List<ArticleVO> listArticles(ArticleQuery articleQuery);

    /**
     * 删除文章
     * @param id 文章id
     * @return 是否删除成功
     */
    boolean deleteById(Long id);

    /**
     * 获取热门文章列表
     * @return 热门文章列表
     */
    List<ArticleVO> listHotArticleList();

    /**
     * 保存文章
     * @param articleDTO 文章信息
     * @return 是否保存成功
     */
    boolean saveArticle(ArticleDTO articleDTO);

    /**
     * 获取文章详情
     * @param id 文章id
     * @return 文章信息
     */
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
