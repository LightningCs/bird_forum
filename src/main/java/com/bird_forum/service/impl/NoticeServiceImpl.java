package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.domain.po.Notice;
import com.bird_forum.domain.po.NoticeRead;
import com.bird_forum.domain.vo.NoticeVO;
import com.bird_forum.service.INoticeReadService;
import com.bird_forum.service.INoticeService;
import com.bird_forum.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author lisa
* @description 针对表【notice(通知)】的数据库操作Service实现
* @createDate 2026-04-05 16:18:07
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements INoticeService{
    
    @Resource
    private INoticeReadService iNoticeReadService;
    
    @Override
    public Page<NoticeVO> pageNotices(Page<Notice> page, Long userId, String isRead) {
        // 根据receiverId查询通知
        LambdaQueryWrapper<Notice> noticeQueryWrapper = new LambdaQueryWrapper<>();
        noticeQueryWrapper.and(wrapper -> wrapper.eq(Notice::getReceiverId, userId)
                .or()
                .eq(Notice::getReceiverId, 0));
        noticeQueryWrapper.orderByDesc(Notice::getCreateTime);
        
        Page<Notice> noticePage = this.page(page, noticeQueryWrapper);
        
        // 转换为VO
        Page<NoticeVO> voPage = new Page<>(noticePage.getCurrent(), noticePage.getSize(), noticePage.getTotal());
        voPage.setRecords(noticePage.getRecords().stream()
            .map(notice -> {
                NoticeVO vo = BeanUtil.copyProperties(notice, NoticeVO.class);
                vo.setIsRead("否"); // 默认未读
                return vo;
            })
            .collect(Collectors.toList()));
        
        // 查询当前用户的通知已读情况
        if (userId != null) {
            LambdaQueryWrapper<NoticeRead> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(NoticeRead::getUserId, userId);
            List<NoticeRead> noticeReads = iNoticeReadService.list(queryWrapper);
            
            // 构建通知ID到已读状态的映射
            Map<Long, String> readMap = noticeReads.stream()
                .collect(Collectors.toMap(NoticeRead::getNoticeId, NoticeRead::getIsRead));
            
            // 设置已读状态
            voPage.getRecords().forEach(vo -> {
                if (readMap.containsKey(vo.getId())) {
                    vo.setIsRead(readMap.get(vo.getId()));
                }
            });
            
            // 如果需要过滤已读/未读
            if (isRead != null) {
                List<NoticeVO> filtered = voPage.getRecords().stream()
                    .filter(vo -> vo.getIsRead().equals(isRead))
                    .collect(Collectors.toList());
                voPage.setRecords(filtered);
                voPage.setTotal(filtered.size());
            }
        }
        
        return voPage;
    }
}