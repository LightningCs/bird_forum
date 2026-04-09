package com.bird_forum.domain.vo;

import com.bird_forum.domain.po.Article;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 历史记录VO
 * @Author: csh
 * @Date: 2025/6/26
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "历史记录VO")
public class HistoryVO implements Serializable {
    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "浏览时间")
    private LocalDateTime viewTime;

    @Schema(description = "文章信息")
    private ArticleVO article;
}
