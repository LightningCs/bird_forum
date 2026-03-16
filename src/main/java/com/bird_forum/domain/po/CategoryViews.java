package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("category_views")
@Schema(name = "分类浏览数量")
public class CategoryViews {

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "浏览数量")
    private Integer views;
}
