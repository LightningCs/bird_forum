package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.domain.po.ArticleViews;
import com.bird_forum.mapper.ArticleViewsMapper;
import com.bird_forum.service.IArticleViewsService;
import org.springframework.stereotype.Service;

@Service
public class ArticleViewsServiceImpl extends ServiceImpl<ArticleViewsMapper, ArticleViews> implements IArticleViewsService {
}
