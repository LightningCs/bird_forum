package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.bird_forum.domain.vo.LoginVO;
import com.bird_forum.domain.vo.UserVO;
import com.bird_forum.exception.UserInfoException;
import com.bird_forum.mapper.FollowMapper;
import com.bird_forum.mapper.UserMapper;
import com.bird_forum.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.EncryptUtils;
import com.bird_forum.util.JWTUtils;
import com.bird_forum.util.MinioUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final FollowMapper followMapper;

    @Override
    public LoginVO login(UserDTO userDTO) {
        String account = userDTO.getAccount(), password = userDTO.getPassword();

        // 账号或者密码为空
        if (account == null || password == null)
            throw new UserInfoException(MessageContext.ACCOUNT_PASSWORD_NULL);

//        try {
//            // 密码加密
//            password = EncryptUtils.md5(password);
//        } catch (Exception e) {
//            throw new RuntimeException("加密失败");
//        }

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

        LoginVO loginVO = new LoginVO();

        loginVO.setToken(JWTUtils.create(user.getId()));
        user.setAvatar(MinioUtils.getFileUrl(user.getAvatar()));
        loginVO.setUser(BeanUtil.copyProperties(user, UserVO.class));

        return loginVO;
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

        try {
            userDTO.setPassword(EncryptUtils.md5(userDTO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("加密失败");
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
    public boolean changeStatus(Long userId, Long code) {
        String status = code == 0 ? "启用" : "封禁";

        // 更新状态
        return lambdaUpdate()
                .set(User::getStatus, status)
                .eq(User::getId, userId)
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

        users.forEach(user -> {
            user.setAvatar(MinioUtils.getFileUrl(user.getAvatar()));
        });
        users = users.subList((userQuery.getPageNo() - 1) * userQuery.getPageSize(), Math.min(userQuery.getPageNo() * userQuery.getPageSize(), users.size()));

        return BeanUtil.copyToList(users, UserVO.class);
    }

    /**
     * 获取粉丝或关注者
     *
     * @param userId 用户id
     * @param isFans 是否是粉丝
     * @return 粉丝或关注者
     */
    @Override
    public List<UserVO> getFansOrFollowers(Long userId, Boolean isFans) {
        // 为空则查询当前用户的粉丝或关注者
        if (ObjectUtil.isNull(userId)) {
            userId = ThreadContext.get();
        }

        List<UserVO> users = userMapper.getFansOrFollowers(userId, isFans);

        // 获取当前登录用户ID，用于判断是否已关注
        Long currentUserId = ThreadContext.get();

        users.forEach(userVO -> {
            userVO.setAvatar(MinioUtils.getFileUrl(userVO.getAvatar()));
            // 当前用户自己则为null
            if (ObjectUtil.isNotNull(currentUserId) && currentUserId.equals(userVO.getId())) {
                userVO.setIsFollowed(null);
            } else if (ObjectUtil.isNotNull(currentUserId)) {
                long count = followMapper.selectCount(new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getFollowerId, currentUserId)
                        .eq(Follow::getTargetId, userVO.getId()));
                userVO.setIsFollowed(count > 0);
            } else {
                userVO.setIsFollowed(false);
            }
        });

        users = new ArrayList<>(users.stream()
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                .collect(Collectors.toMap(UserVO::getId, o -> o, (o1, o2) -> o1))
                .values());

        return users;
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户id
     * @return 用户详情
     */
    @Override
    public UserVO getUserDetail(Long userId) {
        // 查询用户基本信息
        User user = getById(userId);
        if (user == null) {
            user = new User();

            user.setUsername("用户已注销");
            user.setAvatar(MinioUtils.getFileUrl("/avatar/error.png"));

            return BeanUtil.copyProperties(user, UserVO.class);
        }

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        // 获取当前登录用户ID
        Long currentUserId = ThreadContext.get();

        // 如果是当前用户，isFollowed为null
        if (ObjectUtil.isNotNull(currentUserId) && currentUserId.equals(userId)) {
            userVO.setIsFollowed(null);
        } else if (ObjectUtil.isNotNull(currentUserId)) {
            // 查询当前用户是否关注了该用户
            long count = followMapper.selectCount(new LambdaQueryWrapper<Follow>()
                    .eq(Follow::getFollowerId, currentUserId)
                    .eq(Follow::getTargetId, userId));
            userVO.setIsFollowed(count > 0);
        } else {
            // 未登录用户
            userVO.setIsFollowed(false);
        }

        // 查询粉丝数量
        long fansCount = followMapper.selectCount(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getTargetId, userId));
        userVO.setFansCount(fansCount);
        userVO.setAvatar(MinioUtils.getFileUrl(user.getAvatar()));

        return userVO;
    }
}
