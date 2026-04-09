package com.bird_forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.po.ArticleViews;

public interface IArticleViewsService extends IService<ArticleViews> {
    
    /**
     * 增加文章浏览量
     * @param articleId 文章id
     */
    void incrementViewCount(Long articleId);
}
