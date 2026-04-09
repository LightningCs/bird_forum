package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 管理员DTO
 * @Author: csh
 * @Date: 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "管理员DTO")
public class ManagerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "管理员id")
    private Long id;

    @Schema(description = "管理员名称")
    private String name;

    @Schema(description = "管理员账号")
    private String account;

    @Schema(description = "管理员密码")
    private String password;

    @Schema(description = "管理员身份")
    private String identity;

    @Schema(description = "管理员状态")
    private String status;
}
