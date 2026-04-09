package com.bird_forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.po.NoticeRead;

public interface INoticeReadService extends IService<NoticeRead> {
    
    /**
     * 添加通知已读记录
     * @param noticeRead 通知已读信息
     * @return 是否添加成功
     */
    boolean addNoticeRead(NoticeRead noticeRead);
}
