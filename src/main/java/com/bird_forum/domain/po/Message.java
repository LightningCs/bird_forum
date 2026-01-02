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
@TableName("message")
@Schema(description = "消息")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "消息id")
    private Long id;

    @Schema(description = "发送者id")
    private Long sourceId;

    @Schema(description = "接收者id")
    private Long targetId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;


}
