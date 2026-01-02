package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description: 举报理由查询参数
 * @Author: csh
 * @Date: 2025/6/26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(description = "举报理由分页查询参数")
public class ReportReasonQuery extends PageQuery {

    @Schema(description = "举报理由内容")
    private String context;

    @Schema(description = "举报理由状态")
    private String status;
}
