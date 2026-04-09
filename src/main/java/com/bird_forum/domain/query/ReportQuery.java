package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 举报分页查询参数
 * @Author: csh
 * @Date: 2025/6/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "举报分页查询参数")
public class ReportQuery extends PageQuery{

    @Schema(description = "举报人")
    private String reporter;

    @Schema(description = "举报理由id")
    private Long reasonId;

    @Schema(description = "目标类型")
    private String targetType;

    @Schema(description = "状态")
    private String status;
}
