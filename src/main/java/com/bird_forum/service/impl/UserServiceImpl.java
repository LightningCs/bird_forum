package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.context.FormatContext;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.dto.UserDTO;
import com.bird_forum.domain.po.Follow;
import com.bird_forum.domain.po.User;
import com.bird_forum.context.MessageContext;
import com.bird_forum.domain.query.UserQuery;
import com.bird_forum.domain.vo.UserVO;
import com.bird_forum.exception.UserInfoException;
import com.bird_forum.mapper.FollowMapper;
import com.bird_forum.mapper.UserMapper;
import com.bird_forum.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.EncryptUtils;
import com.bird_forum.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public String login(UserDTO userDTO) {
        String account = userDTO.getAccount(), password = userDTO.getPassword();

        // 账号或者密码为空
        if (account == null || password == null)
            throw new UserInfoException(MessageContext.ACCOUNT_PASSWORD_NULL);

        try {
            // 密码加密
            password = EncryptUtils.md5(password);
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }

        // 根据账号密码查询用户信息
        User user = lambdaQuery()
                .eq(User::getAccount, account)
                .eq(User::getPassword, password)
                .one();

        // 查无此人
        if (user == null)
            throw new UserInfoException(MessageContext.USER_NOT_EXIST);

        // 存储id
        ThreadContext.set(user.getId());

        return JWTUtils.create(user.getId());
    }

    @Override
    public boolean register(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getAccount() == null || userDTO.getPassword() == null) {
            throw new UserInfoException(MessageContext.REGISTER_DATA_NULL);
        }

        if (userDTO.getUsername().matches(FormatContext.USERNAME_FORMAT)) {
            throw new UserInfoException(MessageContext.NAME_FORMAT_ERROR);
        }

        if (userDTO.getAccount().matches(FormatContext.ACCOUNT_FORMAT)) {
            throw new UserInfoException(MessageContext.ACCOUNT_FORMAT_ERROR);
        }

        if (!userDTO.getPassword().matches(FormatContext.PASSWORD_FORMAT)) {
            throw new UserInfoException(MessageContext.PASSWORD_FORMAT_ERROR);
        }

        // 用户注册
        return save(BeanUtil.copyProperties(userDTO, User.class));
    }

    @Override
    public boolean modifyPassword(UserDTO userDTO) {
        // 两次输入存在空值
        if (userDTO.getPassword() == null || userDTO.getRePassword() == null)
            throw new UserInfoException(MessageContext.PASSWORD_NULL);

        // 两次输入密码格式不对
        if (!userDTO.getPassword().matches(FormatContext.PASSWORD_FORMAT) || !userDTO.getRePassword().matches(FormatContext.PASSWORD_FORMAT))
            throw new UserInfoException(MessageContext.PASSWORD_FORMAT_ERROR);

        // 两次输入不一致
        if (userDTO.getPassword().equals(userDTO.getRePassword()))
            throw new UserInfoException(MessageContext.PASSWORD_REPASSWORD_NOT_EQUALS);

        String password;

        // 加密
        try {
            password = EncryptUtils.md5(userDTO.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
        }

        // 更新密码
        return lambdaUpdate()
                .set(User::getPassword, password)
                .eq(User::getId, ThreadContext.get())
                .update();
    }

    @Override
    public boolean changeStatus(Long code) {
        String status = code == 0 ? "启用" : "封禁";

        // 更新状态
        return lambdaUpdate()
                .set(User::getStatus, status)
                .eq(User::getId, ThreadContext.get())
                .update();
    }

    @Override
    public boolean destroy() {
        // 注销用户
        return lambdaUpdate()
                .eq(User::getId, ThreadContext.get())
                .remove();
    }

    @Override
    public List<UserVO> list(UserQuery userQuery) {

        // lambda表达式分页查询用户数据
        List<User> users = lambdaQuery()
                .like(userQuery.getUsername() != null, User::getUsername, userQuery.getUsername())
                .eq(userQuery.getSex() != null, User::getSex, userQuery.getSex())
                .eq(userQuery.getStatus() != null, User::getStatus, userQuery.getStatus())
                .list(userQuery.toPage());

        return BeanUtil.copyToList(users, UserVO.class);
    }

    /**
     * 获取粉丝列表
     * @return 粉丝列表
     */
    @Override
    public List<UserVO> listFans() {
        return userMapper.listFans(ThreadContext.get());
    }

    /**
     * 获取关注列表
     * @return 关注列表
     */
    @Override
    public List<UserVO> listFollowers() {
        return userMapper.listFollowers(ThreadContext.get());
    }
}
