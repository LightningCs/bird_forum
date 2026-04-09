package com.bird_forum.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "登录返回数据")
public class LoginVO {
    @Schema(description = "token")
    private String token;

    @Schema(description = "用户信息")
    private UserVO user;
}
