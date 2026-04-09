package com.bird_forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.CommentDTO;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.Comment;
import com.bird_forum.domain.query.CommentQuery;
import com.bird_forum.domain.vo.CommentVO;
import com.bird_forum.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/comment")
@Tag(name = "评论模块")
@Slf4j
public class CommentController {
    @Resource
    private ICommentService iCommentService;

    /**
     * 添加评论
     *
     * @param commentDTO 评论DTO
     * @return 响应数据
     */
    @PostMapping("/add")
    @Operation(summary = "添加评论", description = "添加评论", method = "POST")
    public ResponseData addComment(@RequestBody CommentDTO commentDTO) {
        log.info("添加评论:{}", commentDTO);
        commentDTO.setCommentUserId(ThreadContext.get());

        // 添加评论成功
        if (iCommentService.addComment(commentDTO)) {
            return ResponseData.success();
        }

        // 失败
        return ResponseData.error();
    }

    /**
     * 获取文章的评论
     *
     * @param commentQuery 评论查询参数
     * @return 评论列表
     */
    @GetMapping
    @Operation(summary = "获取文章的评论", description = "获取文章的评论", method = "GET")
    public ResponseData<List<CommentVO>> getComments(CommentQuery commentQuery) {
        log.info("获取文章的评论:{}", commentQuery);

        // 获取当前文章的所有评论
        return ResponseData.success(iCommentService.getComments(commentQuery));
    }

    /**
     * 删除评论
     *
     * @param commentId 评论id
     * @return 响应数据
     */
    @DeleteMapping("/{commentId}")
    @Operation(summary = "删除评论", description = "删除评论", method = "DELETE")
    public ResponseData deleteComment(@PathVariable Long commentId) {
        log.info("删除评论: {}", commentId);

        if (iCommentService.removeById(commentId)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 修改评论
     *
     * @param commentDTO 评论DTO
     * @return 响应数据
     */
    @PutMapping
    @Operation(summary = "修改评论", description = "修改评论", method = "PUT")
    public ResponseData updateComment(@RequestBody CommentDTO commentDTO) {
        log.info("修改评论: {}", commentDTO);

        if (iCommentService.update(commentDTO)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 批量隐藏
     *
     * @return 响应数据
     */
    @PutMapping("hidden/batch")
    @Operation(summary = "批量隐藏", description = "批量隐藏", method = "PUT")
    public ResponseData updateComment(@RequestBody List<Long> ids) {
        log.info("批量隐藏: {}", ids);

        if (iCommentService.update(new LambdaUpdateWrapper<Comment>()
                .set(Comment::getStatus, "不可见")
                .in(Comment::getId, ids))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }


    /**
     * 隐藏
     *
     * @return 响应数据
     */
    @PutMapping("hidden")
    @Operation(summary = "隐藏", description = "隐藏", method = "PUT")
    public ResponseData updateComment(Long id, String status) {
        log.info("{}: {}", status, id);

        if (iCommentService.update(new LambdaUpdateWrapper<Comment>()
                .set(Comment::getStatus, status)
                .eq(Comment::getId, id))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }


    @DeleteMapping("delete/batch")
    @Operation(summary = "批量删除评论", description = "批量删除评论", method = "PUT")
    public ResponseData batchDelete(@RequestBody List<Long> articleIds) {
        log.info("批量删除评论 {}", articleIds);

        try {
            iCommentService.remove(new LambdaQueryWrapper<Comment>()
                    .in(Comment::getId, articleIds));
            return ResponseData.success();
        } catch (Exception e) {
            log.error("批量删除评论失败", e);
            return ResponseData.error("批量删除评论失败");
        }
    }

    @PutMapping("illegal")
    @Operation(summary = "违规", description = "违规", method = "PUT")
    public ResponseData illegal(Long articleId, Integer isIllegal) {
        log.info("评论 {} 是否违规: {}", articleId, isIllegal);

        try {
            iCommentService.update(new LambdaUpdateWrapper<Comment>()
                    .set(Comment::getIsIllegal, isIllegal)
                    .eq(Comment::getId, articleId));
            return ResponseData.success();
        } catch (Exception e) {
            log.error("批量删除评论失败", e);
            return ResponseData.error("批量删除评论失败");
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除", method = "DELETE")
    public ResponseData illegal(@PathVariable Long id) {
        log.info("删除 {} ", id);

        try {
            iCommentService.remove(new LambdaQueryWrapper<Comment>()
                    .eq(Comment::getId, id));
            return ResponseData.success();
        } catch (Exception e) {
            log.error("删除失败!", e);
            return ResponseData.error("删除失败!");
        }
    }
}
