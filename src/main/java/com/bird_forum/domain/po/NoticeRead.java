package com.bird_forum.domain.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "通知已读情况")
public class NoticeRead {
    @Schema(description = "通知id")
    private Long noticeId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "是否已读")
    private String isRead;
}
