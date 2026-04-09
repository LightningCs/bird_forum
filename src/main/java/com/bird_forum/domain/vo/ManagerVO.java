package com.bird_forum.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description: 管理员VO
 * @Author: csh
 * @Date: 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "管理员VO")
public class ManagerVO {

    @Schema(description = "管理员id")
    private Long id;

    @Schema(description = "管理员名称")
    private String name;

    @Schema(description = "管理员账号")
    private String account;

    @Schema(description = "管理员身份")
    private Integer identity;

    @Schema(description = "管理员状态")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
