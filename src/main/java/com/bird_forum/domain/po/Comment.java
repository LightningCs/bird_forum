package com.bird_forum.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("comment")
@Schema(description = "评论")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "评论id")
    private Long id;

    @Schema(description = "内容")
    private String context;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "评论用户id")
    private Long commentUserId;

    @Schema(description = "根评论id")
    private Long rootId;

    @Schema(description = "父评论id")
    private Long parentId;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "点赞数")
    private Long likeNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
