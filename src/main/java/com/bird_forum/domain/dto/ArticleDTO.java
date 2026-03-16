package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Schema(description = "文章DTO")
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "分类")
    private Long[] categories;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "发布者id")
    private Long publisherId;

    @Schema(description = "图片")
    private MultipartFile imageFile;

    @Schema(description = "状态")
    private String status;
}
