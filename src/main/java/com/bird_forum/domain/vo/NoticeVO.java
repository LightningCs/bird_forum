package com.bird_forum.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
* 通知视图对象
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "通知视图")
public class NoticeVO implements Serializable {
    
    @Schema(description = "通知id")
    private Long id;
    
    @Schema(description = "标题")
    private String title;
    
    @Schema(description = "内容")
    private String context;
    
    @Schema(description = "类型")
    private String type;
    
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    @Schema(description = "创建者")
    private String createBy;
    
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    
    @Schema(description = "更新者")
    private String updateBy;
    
    @Schema(description = "当前用户是否已读")
    private String isRead;
}