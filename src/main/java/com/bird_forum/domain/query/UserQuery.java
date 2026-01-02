package com.bird_forum.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户查询条件")
public class UserQuery extends PageQuery {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "状态")
    private String status;
}
