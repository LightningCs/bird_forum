package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description: 评论分页查询参数
 * @Author: csh
 * @Date: 2025/9/27
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "评论分页查询参数")
@ToString
public class CommentQuery extends PageQuery{
    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "根评论id")
    private Long rootId;

    @Schema(description = "评论状态")
    private String status;
}
