package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("article_like_collection")
@Schema(description = "文章点赞收藏")
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLikeCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "类型 1点赞 2收藏")
    private Integer type;
}
