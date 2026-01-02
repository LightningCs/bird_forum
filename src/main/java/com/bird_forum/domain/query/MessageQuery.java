package com.bird_forum.domain.query;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *  查询参数
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "消息")
public class MessageQuery extends PageQuery {

    @Schema(description = "发送者id")
    private Long sourceId;

    @Schema(description = "接收者id")
    private Long targetId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "状态")
    private String status;
}
