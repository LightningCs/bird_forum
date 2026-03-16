package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("manager")
@Schema(description = "管理员")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "管理员id")
    private Long id;

    @Schema(description = "管理员名称")
    private String name;

    @Schema(description = "管理员账号")
    private String account;

    @Schema(description = "管理员密码")
    private String password;

    @Schema(description = "管理员状态")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;


}
