package com.bird_forum.service;

import com.bird_forum.domain.po.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.MessageQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IMessageService extends IService<Message> {
    /**
     * 获取消息列表
     * @param messageQuery 消息查询参数
     * @return 消息列表
     */
    List<Message> list(MessageQuery messageQuery);
}
