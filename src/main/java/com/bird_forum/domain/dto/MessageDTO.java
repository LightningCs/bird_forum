package com.bird_forum.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 *
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "消息DTO")
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "消息id")
    private Long id;

    @Schema(description = "发送者id")
    private Long sourceId;

    @Schema(description = "接收者id")
    private Long targetId;

    @Schema(description = "内容")
    private String content;
}
