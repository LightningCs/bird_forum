package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 举报理由DTO
 * @Author: csh
 * @Date: 2025/6/26
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "举报理由DTO")
public class ReportReasonDTO implements Serializable {

    @Schema(description = "举报理由id")
    private Long id;

    @Schema(description = "举报理由")
    private String context;

    @Schema(description = "状态")
    private String status;
}
