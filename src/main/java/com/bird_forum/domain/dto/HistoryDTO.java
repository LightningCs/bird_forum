package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "历史记录DTO")
public class HistoryDTO {
    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;
}
