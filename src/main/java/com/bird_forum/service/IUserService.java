package com.bird_forum.service;

import com.bird_forum.domain.dto.UserDTO;
import com.bird_forum.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.UserQuery;
import com.bird_forum.domain.vo.UserVO;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IUserService extends IService<User> {

    String login(UserDTO userDTO);

    boolean register(UserDTO userDTO);

    boolean modifyPassword(UserDTO userDTO);

    boolean changeStatus(Long code);

    boolean destroy();

    List<UserVO> list(UserQuery userQuery);

    List<UserVO> listFans();

    List<UserVO> listFollowers();
}
