package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.domain.po.ArticleViews;
import com.bird_forum.mapper.ArticleViewsMapper;
import com.bird_forum.service.IArticleViewsService;
import org.springframework.stereotype.Service;

@Service
public class ArticleViewsServiceImpl extends ServiceImpl<ArticleViewsMapper, ArticleViews> implements IArticleViewsService {
    
    @Override
    public void incrementViewCount(Long articleId) {
        // 查询文章浏览量记录
        LambdaQueryWrapper<ArticleViews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleViews::getArticleId, articleId);
        ArticleViews articleViews = this.getOne(queryWrapper);
        
        if (articleViews != null) {
            // 记录存在，浏览量+1
            articleViews.setViewCount(articleViews.getViewCount() + 1);
            this.update(new LambdaUpdateWrapper<ArticleViews>()
                    .set(ArticleViews::getViewCount, articleViews.getViewCount())
                    .eq(ArticleViews::getArticleId, articleId));
        } else {
            // 记录不存在，创建新记录，浏览量为1
            ArticleViews newViews = new ArticleViews();
            newViews.setArticleId(articleId);
            newViews.setViewCount(1L);
            this.save(newViews);
        }
    }
}
