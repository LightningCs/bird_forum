package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 举报DTO
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "举报DTO")
public class ReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "举报id")
    private Long id;

    @Schema(description = "举报人id")
    private Long reporterId;

    @Schema(description = "举报理由id")
    private Long reasonId;

    @Schema(description = "目标类型")
    private String targetType;

    @Schema(description = "目标id")
    private Long targetId;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "状态")
    private String status;
}
