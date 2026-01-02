package com.bird_forum.service.impl;

import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.ArticleCategory;
import com.bird_forum.domain.query.ArticleQuery;
import com.bird_forum.mapper.ArticleMapper;
import com.bird_forum.service.IArticleCategoryService;
import com.bird_forum.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private IArticleCategoryService iArticleCategoryService;

    /**
     * 获取文章列表
     * @param articleQuery 查询参数
     * @return 文章列表
     */
    @Override
    public List<Article> listArticles(ArticleQuery articleQuery) {
        // TODO 分页查询
        List<Article> articles = articleMapper.listArticles(articleQuery);

        return articles;
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 是否删除成功
     */
    @Override
    public boolean deleteById(Long id) {
        // 删除文章
        Boolean r1 = articleMapper.deleteById(id) > 0;
        // 删除文章和分类关系
        Boolean r2 = iArticleCategoryService.lambdaUpdate()
                .eq(ArticleCategory::getArticleId, id)
                .remove();

        return r1 && r2;
    }
}
