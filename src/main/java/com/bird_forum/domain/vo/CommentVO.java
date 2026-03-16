package com.bird_forum.domain.vo;

import com.bird_forum.domain.po.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "所属文章标题")
    private String articleTitle;

    @Schema(description = "是否点赞")
    private Boolean isLike;

    @Schema(description = "子评论列表")
    private List<CommentVO> children;

    @Schema(description = "被回复人id")
    private Long replyUserId;

    @Schema(description = "被回复人用户名")
    private String replyUserName;

    @Schema(description = "被回复人头像")
    private String replyAvatar;

}
