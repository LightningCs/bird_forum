package com.bird_forum.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 举报VO
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "举报VO")
public class ReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "举报id")
    private Long id;

    @Schema(description = "举报人id")
    private Long reporterId;

    @Schema(description = "举报理由id")
    private Long reasonId;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "状态")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
