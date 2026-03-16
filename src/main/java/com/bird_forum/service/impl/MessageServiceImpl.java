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
     * 消息列表（两个用户之间的聊天记录）
     *
     * @param messageQuery 查询参数（sourceId 和 targetId 为两个用户的id）
     * @return 消息列表
     */
    @Override
    public List<Message> list(MessageQuery messageQuery) {
        return lambdaQuery()
                .and(BeanUtils.isNotNull(messageQuery.getSourceId()) && BeanUtils.isNotNull(messageQuery.getTargetId()),
                        w -> w.and(q -> q.eq(Message::getSourceId, messageQuery.getSourceId())
                                         .eq(Message::getTargetId, messageQuery.getTargetId()))
                              .or(q -> q.eq(Message::getSourceId, messageQuery.getTargetId())
                                        .eq(Message::getTargetId, messageQuery.getSourceId()))
                )
                .like(BeanUtils.isNotNull(messageQuery.getContent()), Message::getContent, messageQuery.getContent())
                .eq(BeanUtils.isNotNull(messageQuery.getStatus()), Message::getStatus, messageQuery.getStatus())
                .orderByAsc(Message::getSendTime)
                .list(messageQuery.toPage());
    }
}
