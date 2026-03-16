package com.bird_forum.mapper;

import com.bird_forum.domain.dto.UserDTO;
import com.bird_forum.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 获取粉丝列表
     * @return 粉丝列表
     */
    List<UserVO> listFans(Long followerId);

    /**
     * 获取关注列表
     * @return 关注列表
     */
    List<UserVO> listFollowers(Long targetId);

    /**
     * 获取粉丝或关注者
     *
     * @param userId 用户id
     * @param isFans 是否是粉丝
     * @return 粉丝或关注者
     */
    List<UserVO> getFansOrFollowers(Long userId, Boolean isFans);
}
