package com.bird_forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.po.Follow;
import com.bird_forum.domain.po.User;
import com.bird_forum.domain.vo.UserVO;
import com.bird_forum.service.IFollowService;
import com.bird_forum.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/follow")
@Tag(name = "关注模块")
public class FollowController {
    @Resource
    private IFollowService followService;

    @Resource
    private IUserService userService;

    /**
     * 关注用户
     *
     * @param targetId 目标用户id
     * @return 响应数据
     */
    @PostMapping("/{userId}/{targetId}")
    @Operation(summary = "关注用户", description = "关注用户", method = "POST")
    public ResponseData follow(@PathVariable Long userId, @PathVariable Long targetId) {
        Follow follow = Follow.builder()
                .targetId(targetId)
                .followerId(userId)
                .build();

        // 关注用户
        if (followService.save(follow)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 取消关注
     *
     * @param targetId 目标用户id
     * @return 响应数据
     */
    @DeleteMapping("/{userId}/{targetId}")
    @Operation(summary = "取消关注", description = "取消关注", method = "DELETE")
    public ResponseData unfollow(@PathVariable Long userId, @PathVariable Long targetId) {
        // 删除关注关系
        if (followService.remove(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getTargetId, targetId)
                        .eq(Follow::getFollowerId, userId)
        )) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 获取粉丝或关注者
     *
     * @param userId   用户id
     * @param isFans   是否获取粉丝
     * @return 粉丝或关注者列表
     */
    @GetMapping
    @Operation(summary = "获取粉丝或关注者", description = "获取粉丝或关注者", method = "GET")
    public ResponseData<List<UserVO>> getFansOrFollowers(@Schema(description = "用户id") Long userId,
                                                         @Schema(description = "是否获取粉丝") Boolean isFans) {
        return ResponseData.success(userService.getFansOrFollowers(userId, isFans));
    }
}
