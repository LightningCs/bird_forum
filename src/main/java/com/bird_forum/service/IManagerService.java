package com.bird_forum.service;

import com.bird_forum.domain.dto.ManagerDTO;
import com.bird_forum.domain.po.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.ManagerQuery;
import com.bird_forum.domain.vo.ManagerVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IManagerService extends IService<Manager> {

    List<ManagerVO> listManagers(ManagerQuery query);

    ManagerVO getManagerById(Long id);

    boolean addManager(ManagerDTO dto);

    boolean updateManager(ManagerDTO dto);

    boolean deleteManager(Long id);
}
