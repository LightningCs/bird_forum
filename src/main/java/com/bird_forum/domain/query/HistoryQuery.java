package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "历史记录查询参数")
public class HistoryQuery extends PageQuery {
    @Schema(description = "用户id")
    private Long userId;
}
