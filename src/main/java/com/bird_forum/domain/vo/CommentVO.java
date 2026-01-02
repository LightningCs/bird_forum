package com.bird_forum.domain.vo;

import com.bird_forum.domain.po.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 评论VO
 * @Author: csh
 * @Date: 2025/6/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "评论VO")
public class CommentVO extends Comment {

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "是否点赞")
    private Boolean isLike;

}
