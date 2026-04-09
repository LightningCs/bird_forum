package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 管理员查询参数
 * @Author: csh
 * @Date: 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "管理员查询参数")
public class ManagerQuery extends PageQuery {

    @Schema(description = "管理员名称")
    private String name;

    @Schema(description = "管理员身份")
    private Integer identity;

    @Schema(description = "管理员状态")
    private String status;
}
