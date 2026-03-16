package com.bird_forum.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description: 用户信息VO
 * @Author: csh
 * @Date: 2025/6/26
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户信息VO")
public class UserVO {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户留言")
    private String message;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "粉丝数量")
    private Long fansCount;

    @Schema(description = "是否关注（当前用户为null，其他用户为boolean）")
    private Boolean isFollowed;

    @Schema(description = "最后一条消息内容")
    private String lastMessage;

    @Schema(description = "最后一条消息时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastMessageTime;

    @Schema(description = "未读消息数量")
    private Long unreadCount;

    // 设置时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
