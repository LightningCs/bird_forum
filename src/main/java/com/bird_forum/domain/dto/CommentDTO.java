package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 评论DTO
 * @Author: csh
 * @Date: 2025/9/27
 **/
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(description = "评论DTO")
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long id;

    /**
     * 评论内容
     */
    @Schema(description = "内容")
    private String context;

    /**
     * 文章id
     */
    @Schema(description = "文章id")
    private Long articleId;

    /**
     * 评论用户id
     */
    @Schema(description = "评论用户id")
    private Long commentUserId;

    /**
     * 根评论id
     */
    @Schema(description = "根评论id")
    private Long rootId;

    /**
     * 父评论id
     */
    @Schema(description = "父评论id")
    private Long parentId;

}
