package com.bird_forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.po.ArticleLikeCollection;
import com.bird_forum.service.IArticleLikeCollectionService;
import com.bird_forum.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Slf4j
@RestController
@RequestMapping("/article-like-collection")
@Tag(name = "文章点赞收藏模块")
public class ArticleLikeCollectionController {
    @Resource
    private IArticleLikeCollectionService iArticleLikeCollectionService;

    @Resource
    private IArticleService iArticleService;

    /**
     * 文章点赞收藏
     * @param articleId 文章id
     * @param type 类型
     * @return 操作结果
     */
    @PostMapping("/{userId}/{articleId}/{type}")
    @Operation(summary = "文章点赞收藏", description = "文章点赞收藏", method = "POST")
    public ResponseData likeCollection(@PathVariable Long userId, @PathVariable Long articleId, @PathVariable Integer type) {
        log.info("添加: {}, 类型: {}", articleId, type);

        try {
            iArticleLikeCollectionService.save(new ArticleLikeCollection(articleId, userId, type));

            if (type == 1) {
                iArticleService.update()
                        .setSql("like_num = like_num + 1")
                        .eq("id", articleId)
                        .update();
            }
        } catch (Exception e) {
            // 已存在则删除
            iArticleLikeCollectionService.remove(new LambdaQueryWrapper<ArticleLikeCollection>()
                    .eq(ArticleLikeCollection::getArticleId, articleId)
                    .eq(ArticleLikeCollection::getUserId, userId)
                    .eq(ArticleLikeCollection::getType, type));

            if (type == 1) {
                iArticleService.update()
                        .setSql("like_num = like_num - 1")
                        .eq("id", articleId)
                        .update();
            }
        }
        return ResponseData.success();
    }

    /**
     * 取消文章点赞收藏
     * @param articleId 文章id
     * @param type 类型
     * @return 操作结果
     */
    @DeleteMapping("/{articleId}/{type}")
    @Operation(summary = "取消文章点赞收藏", description = "取消文章点赞收藏", method = "DELETE")
    public ResponseData dislikeCollection(@PathVariable Long articleId, @PathVariable Integer type) {
        log.info("取消: {}, 类型: {}", articleId, type);

        if (iArticleLikeCollectionService.remove(new LambdaQueryWrapper<ArticleLikeCollection>()
                .eq(ArticleLikeCollection::getArticleId, articleId)
                .eq(ArticleLikeCollection::getUserId, ThreadContext.get())
                .eq(ArticleLikeCollection::getType, type))) {

            if (type == 1) {
                iArticleService.update()
                        .setSql("like_num = like_num - 1")
                        .eq("id", articleId)
                        .update();
            }

            return ResponseData.success();
        }

        return ResponseData.error();
    }
}
