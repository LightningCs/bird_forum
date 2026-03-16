package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * @Description: 文章查询参数
 * @Author: csh
 * @Date: 2025/6/26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(description = "文章分页查询参数")
public class ArticleQuery extends PageQuery{
    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "文章分类")
    private String category;

    @Schema(description = "类型 1点赞 2收藏")
    private Integer type;
}
