package com.bird_forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.po.Follow;
import com.bird_forum.service.IFollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 关注用户
     *
     * @param targetId 目标用户id
     * @return 响应数据
     */
    @PostMapping
    @Operation(summary = "关注用户", description = "关注用户", method = "POST")
    public ResponseData follow(@Schema(description = "目标用户id") Long targetId) {
        Follow follow = Follow.builder()
                .targetId(targetId)
                .followerId(ThreadContext.get())
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
    @DeleteMapping("/{targetId}")
    @Operation(summary = "取消关注", description = "取消关注", method = "DELETE")
    public ResponseData unfollow(@Schema(description = "目标用户id") @PathVariable Long targetId) {
        // 删除关注关系
        if (followService.remove(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getTargetId, targetId)
                        .eq(Follow::getFollowerId, ThreadContext.get())
        )) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    // TODO 获取粉丝或关注者
}
