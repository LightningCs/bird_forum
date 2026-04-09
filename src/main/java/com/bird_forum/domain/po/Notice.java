package com.bird_forum.domain.po;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import java.util.Date;

/**
* 通知
* @TableName notice
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "通知")
public class Notice implements Serializable {

    /**
    * id
    */
    @NotNull(message="[id]不能为空")
    @Schema(description = "id")
    private Long id;
    /**
    * 标题
    */
    @NotBlank(message="[标题]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @Schema(description = "标题")
    private String title;
    /**
    * 内容
    */
    @NotBlank(message="[内容]不能为空")
    @Size(max= -1,message="编码长度不能超过-1")
    @Schema(description = "内容")
    private String context;
    /**
    * 类型
    */
    @NotBlank(message="[类型]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    @Schema(description = "类型")
    private String type;
    /**
     * 接收者id(全部则为0)
     */
    @Schema(description = "接收者id(全部则为0)")
    private Long receiverId;
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    @Schema(description = "创建时间")
    private Date createTime;
    /**
    * 创建者
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Schema(description = "创建者")
    private String createBy;
    /**
    * 更新时间
    */
    @Schema(description = "更新时间")
    private Date updateTime;
    /**
    * 更新者
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Schema(description = "更新者")
    private String updateBy;

}
