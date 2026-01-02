package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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
@TableName("history")
@Builder
@Schema(description = "历史记录")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "最近浏览时间")
    private LocalDateTime viewTime;
}
