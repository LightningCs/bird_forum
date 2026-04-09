package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.domain.po.NoticeRead;
import com.bird_forum.mapper.NoticeReadMapper;
import com.bird_forum.service.INoticeReadService;
import org.springframework.stereotype.Service;

@Service
public class NoticeReadServiceImpl extends ServiceImpl<NoticeReadMapper, NoticeRead> implements INoticeReadService {
    
    @Override
    public boolean addNoticeRead(NoticeRead noticeRead) {
        return this.save(noticeRead);
    }
}
