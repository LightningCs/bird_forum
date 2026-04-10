package com.bird_forum.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.Comment;
import com.bird_forum.domain.po.Report;
import com.bird_forum.service.IArticleService;
import com.bird_forum.service.ICommentService;
import com.bird_forum.service.IReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@Tag(name = "统计模块")
@Slf4j
public class StatisticsDataController {

    @Resource
    private IArticleService iArticleService;

    @Resource
    private ICommentService iCommentService;

    @Resource
    private IReportService iReportService;

    @GetMapping
    @Operation(summary = "获取统计数据", description = "获取统计数据", method = "GET")
    public ResponseData<Map<String, Long>> getStatisticData() {
        log.info("获取统计数据");

        Map<String, Long> res = new HashMap<>();
        Date now = new Date();

        DateTime begin = DateUtil.beginOfDay(now);
        DateTime end = DateUtil.endOfDay(now);

        res.put("articleCount", iArticleService.count(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsIllegal, 0)
                .eq(Article::getStatus, "已发布")));
        res.put("todayArticleCount", iArticleService.count(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsIllegal, 0)
                .eq(Article::getStatus, "已发布")
                .ge(Article::getCreateTime, begin)
                .le(Article::getCreateTime, end)));
        res.put("commentCount", iCommentService.count(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getIsIllegal, 0)
                .eq(Comment::getStatus, "可见")));
        res.put("todayCommentCount", iCommentService.count(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getIsIllegal, 0)
                .eq(Comment::getStatus, "可见")
                .ge(Comment::getCreateTime, begin)
                .le(Comment::getCreateTime, end)));
        res.put("reportCount", iReportService.count(new LambdaQueryWrapper<Report>()
                .eq(Report::getStatus, "未处理")));

        return ResponseData.success(res);
    }
}
