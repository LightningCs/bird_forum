package com.bird_forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.po.ArticleCategory;
import com.bird_forum.domain.vo.CategoryVO;
import com.bird_forum.service.IArticleCategoryService;
import com.bird_forum.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/article-category")
@Tag(name = "文章分类模块")
@Slf4j
public class ArticleCategoryController {
    @Resource
    private IArticleCategoryService iArticleCategoryService;

    @Resource
    private ICategoryService iCategoryService;

    /**
     * 添加文章分类
     *
     * @param articleId  文章id
     * @param categoryId 分类id
     * @return 响应数据
     */
    @PostMapping
    @Operation(summary = "添加文章分类", description = "添加文章分类", method = "POST")
    public ResponseData addCategory(Long articleId, Long categoryId) {
        log.info("新增 ——> 文章: {}, 分类: {}", articleId, categoryId);

        if (iArticleCategoryService.save(new ArticleCategory(articleId, categoryId))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 批量添加文章分类
     *
     * @param articleId   文章id
     * @param categoryIds 分类id集合
     * @return 响应数据
     */
    @PostMapping("/batch")
    @Operation(summary = "批量添加文章分类", description = "批量添加文章分类", method = "POST")
    public ResponseData addCategories(Long articleId, List<Long> categoryIds) {
        log.info("新增 ——> 文章: {}, 分类集: {}", articleId, categoryIds);

        List<ArticleCategory> articleCategories = categoryIds.stream()
                .map(categoryId -> new ArticleCategory(articleId, categoryId))
                .toList();

        if (iArticleCategoryService.saveBatch(articleCategories)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 删除文章分类
     *
     * @param articleId  文章id
     * @param categoryId 分类id
     * @return 响应数据
     */
    @DeleteMapping
    @Operation(summary = "删除文章分类", description = "删除文章分类", method = "DELETE")
    public ResponseData deleteCategory(Long articleId, Long categoryId) {
        log.info("删除 ——> 文章: {}, 分类: {}", articleId, categoryId);

        if (iArticleCategoryService.remove(new LambdaQueryWrapper<ArticleCategory>()
                .eq(ArticleCategory::getArticleId, articleId)
                .eq(ArticleCategory::getCategoryId, categoryId))
        ) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 批量删除文章分类
     *
     * @param articleId   文章id
     * @param categoryIds 分类id集合
     * @return 响应数据
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除文章分类", description = "批量删除文章分类", method = "DELETE")
    public ResponseData deleteCategories(Long articleId, List<Long> categoryIds) {
        log.info("批量删除 ——> 文章: {}, 分类集: {}", articleId, categoryIds);

        if (iArticleCategoryService.remove(new LambdaQueryWrapper<ArticleCategory>()
                .eq(ArticleCategory::getArticleId, articleId)
                .in(ArticleCategory::getCategoryId, categoryIds))
        ) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 查询文章分类
     *
     * @param articleId 文章id
     * @return 分类集合
     */
    @GetMapping("/{articleId}")
    @Operation(summary = "查询文章分类", description = "查询文章分类", method = "GET")
    public ResponseData<List<CategoryVO>> queryCategories(@PathVariable Long articleId) {
        log.info("查询 ——> 文章: {}", articleId);

        return ResponseData.success(iCategoryService.list(articleId));
    }
}
