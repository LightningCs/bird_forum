package com.bird_forum.service.impl;

import com.bird_forum.domain.po.Manager;
import com.bird_forum.mapper.ManagerMapper;
import com.bird_forum.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
