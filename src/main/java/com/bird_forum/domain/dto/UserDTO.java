package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 用户DTO
 * @Author: csh
 * @Date: 2025/6/26
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 重复密码
     */
    @Schema(description = "重复密码")
    private String rePassword;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String message;
}
