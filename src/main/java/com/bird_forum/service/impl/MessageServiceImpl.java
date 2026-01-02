package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.domain.po.Message;
import com.bird_forum.domain.query.MessageQuery;
import com.bird_forum.mapper.MessageMapper;
import com.bird_forum.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.BeanUtils;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    /**
     * 消息列表
     *
     * @param messageQuery 查询参数
     * @return 消息列表
     */
    @Override
    public List<Message> list(MessageQuery messageQuery) {
        return lambdaQuery()
                .eq(BeanUtils.isNotNull(messageQuery.getSourceId()), Message::getSourceId, messageQuery.getSourceId())
                .eq(BeanUtils.isNotNull(messageQuery.getTargetId()), Message::getTargetId, messageQuery.getTargetId())
                .like(BeanUtils.isNotNull(messageQuery.getContent()), Message::getContent, messageQuery.getContent())
                .eq(BeanUtils.isNotNull(messageQuery.getStatus()), Message::getStatus, messageQuery.getStatus())
                .list(messageQuery.toPage());
    }
}
