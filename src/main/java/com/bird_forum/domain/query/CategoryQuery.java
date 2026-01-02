package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * @Description: 分类查询参数
 * @Author: csh
 * @Date: 2025/6/26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(description = "分类分页查询参数")
public class CategoryQuery extends PageQuery {
    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "状态")
    private String status;
}
