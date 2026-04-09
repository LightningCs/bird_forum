package com.bird_forum.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Description: 评论分页查询参数
 * @Author: csh
 * @Date: 2025/9/27
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "评论分页查询参数")
@ToString
public class CommentQuery extends PageQuery {

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "根评论id")
    private Long rootId;

    @Schema(description = "评论状态")
    private String status;

    @Schema(description = "评论内容（模糊）")
    private String context;

    @Schema(description = "发布者名称（模糊）")
    private String username;

    @Schema(description = "文章标题（模糊）")
    private String title;

    @Schema(description = "是否违规")
    private Boolean isIllegal;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
}
