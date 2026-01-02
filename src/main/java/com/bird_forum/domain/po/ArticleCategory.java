package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("article_category")
@Schema(description = "文章分类")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "分类id")
    private Long categoryId;


}
