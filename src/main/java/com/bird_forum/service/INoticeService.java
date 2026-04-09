package com.bird_forum.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.po.Notice;
import com.bird_forum.domain.po.NoticeRead;
import com.bird_forum.domain.query.PageQuery;
import com.bird_forum.domain.vo.NoticeVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author lisa
* @description 针对表【notice(通知)】的数据库操作Service
* @createDate 2026-04-05 16:18:07
*/
public interface INoticeService extends IService<Notice> {
    
    /**
     * 分页查询通知列表
     * @param page 分页参数

 * @param userId 用户ID
     * @param isRead 是否已读
     * @return 分页结果
     */
    Page<NoticeVO> pageNotices(Page<Notice> page, Long userId, String isRead);
}
