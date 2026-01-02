package com.bird_forum.service;

import com.bird_forum.domain.po.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.ArticleQuery;

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
    List<Article> listArticles(ArticleQuery articleQuery);

    /**
     * 删除文章
     * @param id 文章id
     * @return 是否删除成功
     */
    boolean deleteById(Long id);
}
