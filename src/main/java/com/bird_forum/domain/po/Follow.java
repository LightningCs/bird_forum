package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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
@TableName("follow")
@Builder
@Schema(description = "关注")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "被关注者ID")
    private Long targetId;

    @Schema(description = "关注者ID")
    private Long followerId;
}
