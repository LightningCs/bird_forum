package com.bird_forum.domain.vo;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bird_forum.domain.po.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("article")
@Schema(description = "文章VO")
public class ArticleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "发布者id")
    private Long publisherId;

    @Schema(description = "发布者名称")
    private String publisherName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "是否违规(0-不违规, 1-违规)")
    private Integer isIllegal;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "点赞数")
    private Long likeNum;

    @Schema(description = "收藏数")
    private Long collectNum;

    @Schema(description = "浏览量")
    private Long viewNum;

    @Schema(description = "分类列表")
    private List<Category> categories;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
