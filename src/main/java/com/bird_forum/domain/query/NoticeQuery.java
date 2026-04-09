package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(description = "消息查询参数")
public class NoticeQuery extends PageQuery {
    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "是否已读")
    private String isRead;
    
    @Schema(description = "接收者id(0-全部用户，指定ID-该用户)")
    private Long receiverId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "类型")
    private String type;

}
