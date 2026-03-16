package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article_views")
@Schema(name = "文章浏览量")
public class ArticleViews {

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "浏览量")
    private Long viewCount;
}
