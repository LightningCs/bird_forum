package com.bird_forum.controller;

import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.UserDTO;
import com.bird_forum.domain.query.UserQuery;
import com.bird_forum.domain.vo.LoginVO;
import com.bird_forum.domain.vo.UserVO;
import com.bird_forum.service.IUserService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "用户模块")
public class UserController {
    public final IUserService userService;

    /**
     * 登录
     * @param userDTO 用户信息DTO
     * @return 响应数据
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录", method = "POST")
    public ResponseData<LoginVO> login(@RequestBody UserDTO userDTO) {
        log.info("登录:{}", userDTO.toString());

        // 登录
        return ResponseData.success(userService.login(userDTO));
    }

    /**
     * 注册
     * @param userDTO 用户信息DTO
     * @return 响应数据
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册", method = "POST")
    public ResponseData register(@RequestBody UserDTO userDTO) {
        log.info("注册:{}", userDTO.toString());

        // 注册
        if(userService.register(userDTO)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 更新用户密码
     * @param userDTO 用户信息DTO
     * @return 响应数据
     */
    @PutMapping("/password")
    @Operation(summary = "修改用户密码", description = "修改用户密码", method = "PUT")
    public ResponseData modifyPassword(@RequestBody UserDTO userDTO) {
        log.info("旧密码:{}, 新密码:{}", userDTO.getPassword(), userDTO.getRePassword());

        if (StringUtils.isEmpty(userDTO.getCode()) || !userDTO.getCode().equals(MailController.codeMap.get(userDTO.getAccount()))) {
            return ResponseData.error("验证码错误!");
        }

        // 修改密码
        if (userService.modifyPassword(userDTO)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 更新用户状态
     * @param code 状态
     * @return 响应数据
     */
    @PutMapping("/status/{userId}/{code}")
    @Operation(summary = "修改用户状态", description = "修改用户状态", method = "PUT")
    public ResponseData changeStatus(@PathVariable Long userId, @PathVariable Long code) {
        log.info("用户状态码:{}", code);

        // 根据状态码更新状态
        if (userService.changeStatus(userId, code)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 注销用户
     * @return 响应数据
     */
    @DeleteMapping
    @Operation(summary = "注销用户", description = "注销用户", method = "DELETE")
    public ResponseData destroy() {
        log.info("注销用户");

        if (userService.destroy()) {
            ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 查询用户列表
     * @param userQuery 查询条件
     * @return 响应数据
     */
    @GetMapping
    @Operation(summary = "查询用户", description = "查询用户", method = "GET")
    public ResponseData<List<UserVO>> list(UserQuery userQuery) {
        log.info("查询条件:{}", userQuery);

        return ResponseData.success(userService.list(userQuery));
    }

    /**
     * 获取用户详情
     * @param userId 用户id
     * @return 响应数据
     */
    @GetMapping("/{userId}/{currentUserId}")
    @Operation(summary = "获取用户详情", description = "获取用户详情", method = "GET")
    public ResponseData<UserVO> getUserDetail(@PathVariable Long userId, @PathVariable Long currentUserId) {
        log.info("获取用户详情, userId:{}", userId);

        return ResponseData.success(userService.getUserDetail(userId, currentUserId));
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除用户", description = "批量删除用户", method = "DELETE")
    public ResponseData batchDelete(@RequestBody List<Long> userIds) {
        log.info("批量删除用户, userIds:{}", userIds);

        if (userService.removeByIds(userIds)) {
            return ResponseData.success("删除成功!");
        }

        return ResponseData.error("删除失败!");
    }
}
