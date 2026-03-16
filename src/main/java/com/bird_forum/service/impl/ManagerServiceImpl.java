package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bird_forum.domain.dto.ManagerDTO;
import com.bird_forum.domain.po.Manager;
import com.bird_forum.domain.query.ManagerQuery;
import com.bird_forum.domain.vo.ManagerVO;
import com.bird_forum.mapper.ManagerMapper;
import com.bird_forum.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Override
    public List<ManagerVO> listManagers(ManagerQuery query) {
        List<Manager> managers = lambdaQuery()
                .like(ObjectUtil.isNotNull(query.getName()), Manager::getName, query.getName())
                .like(ObjectUtil.isNotNull(query.getAccount()), Manager::getAccount, query.getAccount())
                .eq(ObjectUtil.isNotNull(query.getStatus()), Manager::getStatus, query.getStatus())
                .orderByDesc(Manager::getCreateTime)
                .list(query.toPage());

        return BeanUtil.copyToList(managers, ManagerVO.class);
    }

    @Override
    public ManagerVO getManagerById(Long id) {
        return BeanUtil.copyProperties(getById(id), ManagerVO.class);
    }

    @Override
    public boolean addManager(ManagerDTO dto) {
        return save(BeanUtil.copyProperties(dto, Manager.class));
    }

    @Override
    public boolean updateManager(ManagerDTO dto) {
        return updateById(BeanUtil.copyProperties(dto, Manager.class));
    }

    @Override
    public boolean deleteManager(Long id) {
        return removeById(id);
    }
}
