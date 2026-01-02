package com.bird_forum.controller;


import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.service.ICommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/comment-like")
@Tag(name = "评论点赞模块")
@Slf4j
public class CommentLikeController {
    @Resource
    private ICommentLikeService iCommentLikeService;

    /**
     * 添加评论点赞
     *
     * @param commentId 评论id
     * @return 响应数据
     */
    @PostMapping("/{commentId}")
    @Operation(summary = "添加评论点赞", description = "添加评论点赞", method = "POST")
    public ResponseData like(@PathVariable Long commentId) {
        log.info("添加评论点赞: {}", commentId);

        if (iCommentLikeService.like(ThreadContext.get(), commentId)) {
            // TODO 评论点赞数量+1

            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 删除评论点赞
     *
     * @param commentId 评论id
     * @return 响应数据
     */
    @DeleteMapping("/{commentId}")
    @Operation(summary = "删除评论点赞", description = "删除评论点赞", method = "DELETE")
    public ResponseData dislike(@PathVariable Long commentId) {
        log.info("删除评论点赞: {}", commentId);

        if (iCommentLikeService.dislike(ThreadContext.get(), commentId)) {
            // TODO 评论点赞数量-1

            return ResponseData.success();
        }

        return ResponseData.error();
    }
}
