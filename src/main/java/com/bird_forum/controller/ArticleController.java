package com.bird_forum.controller;


import cn.hutool.core.bean.BeanUtil;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.ArticleDTO;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.query.ArticleQuery;
import com.bird_forum.service.IArticleService;
import com.bird_forum.util.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/article")
@Tag(name = "文章模块")
@Slf4j
public class ArticleController {
    @Resource
    private IArticleService iArticleService;

    /**
     * 获取文章列表
     * @return 文章列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取文章列表", description = "获取文章列表", method = "GET")
    public ResponseData<List<Article>> list(ArticleQuery articleQuery) {
        log.info("获取文章列表: {}", articleQuery);

        return ResponseData.success(iArticleService.listArticles(articleQuery));
    }

    /**
     * 根据id获取文章详情
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping
    @Operation(summary = "获取文章详情", description = "获取文章详情", method = "GET")
    public ResponseData<Article> getById(Long id) {
        log.info("获取文章 {} 的详细信息", id);

        return ResponseData.success(iArticleService.getById(id));
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章", description = "删除文章", method = "DELETE")
    public ResponseData delete(@PathVariable Long id) {
        log.info("删除文章 {}", id);

        if (iArticleService.deleteById(id)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 修改文章信息
     * @param articleDTO 文章信息
     * @return 是否更新成功
     */
    @PutMapping
    @Operation(summary = "修改文章信息", description = "修改文章信息", method = "PUT")
    public ResponseData update(@RequestBody ArticleDTO articleDTO) {
        log.info("更新文章 {}", articleDTO);

        if (iArticleService.updateById(BeanUtil.copyProperties(articleDTO, Article.class))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    // TODO 新增文章
}
