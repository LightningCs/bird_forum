package com.bird_forum.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.ArticleDTO;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.ArticleLikeCollection;
import com.bird_forum.domain.po.ArticleViews;
import com.bird_forum.domain.query.ArticleQuery;
import com.bird_forum.domain.vo.ArticleVO;
import com.bird_forum.service.IArticleLikeCollectionService;
import com.bird_forum.service.IArticleService;
import com.bird_forum.service.IArticleViewsService;
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
    public ResponseData<List<ArticleVO>> list(ArticleQuery articleQuery) {
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
    public ResponseData<ArticleVO> getById(Long id) {
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

    @PostMapping
    @Operation(summary = "新增文章", description = "新增文章", method = "POST")
    public ResponseData add(@ModelAttribute ArticleDTO articleDTO) {
        log.info("新增文章 {}", articleDTO);

        try {
            // 保存文章
            iArticleService.saveArticle(articleDTO);
        } catch (Exception e) {
            return ResponseData.error();
        }

        return ResponseData.success();
    }

    @GetMapping("/hot/list")
    @Operation(summary = "获取热门文章列表", description = "获取热门文章列表", method = "GET")
    public ResponseData<List<ArticleVO>> getHotList() {
        log.info("获取热门文章列表");

        return ResponseData.success(iArticleService.listHotArticleList());
    }

    /**
     * 获取用户发布的文章列表
     * @param userId 用户id
     * @return 文章列表
     */
    @GetMapping("/publisher/{userId}")
    @Operation(summary = "获取用户发布的文章", description = "获取用户发布的文章", method = "GET")
    public ResponseData<List<ArticleVO>> listByPublisher(@PathVariable Long userId) {
        log.info("获取用户 {} 发布的文章", userId);

        return ResponseData.success(iArticleService.listByPublisher(userId));
    }

    /**
     * 获取用户收藏的文章列表
     * @param userId 用户id
     * @return 文章列表
     */
    @GetMapping("/collected/{userId}")
    @Operation(summary = "获取用户收藏的文章", description = "获取用户收藏的文章", method = "GET")
    public ResponseData<List<ArticleVO>> listCollected(@PathVariable Long userId) {
        log.info("获取用户 {} 收藏的文章", userId);

        return ResponseData.success(iArticleService.listCollected(userId));
    }
}
