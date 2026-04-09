package com.bird_forum.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.NoticeDTO;
import com.bird_forum.domain.po.Notice;
import com.bird_forum.domain.po.NoticeRead;
import com.bird_forum.domain.query.NoticeQuery;
import com.bird_forum.domain.vo.NoticeVO;
import com.bird_forum.service.INoticeReadService;
import com.bird_forum.service.INoticeService;
import com.bird_forum.domain.query.PageQuery;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通知控制器
 */
@RestController
@RequestMapping("/notice")
@Tag(name = "通知模块")
@Slf4j
public class NoticeController {
    
    @Resource
    private INoticeService iNoticeService;
    
    @Resource
    private INoticeReadService iNoticeReadService;
    
    /**
     * 分页查询通知列表
     * @param noticeQuery 查询实体
     * @return 通知列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询通知列表", description = "分页查询通知列表，支持按用户ID、已读状态", method = "GET")
    public ResponseData<List<NoticeVO>> pageNotices(NoticeQuery noticeQuery) {
        log.info("分页查询通知列表，userId: {}, isRead: {}",
            noticeQuery.getUserId(), noticeQuery.getIsRead());

        Page<NoticeVO> voPage = iNoticeService.pageNotices(
            noticeQuery.toPage(), 
            noticeQuery.getUserId(), 
            noticeQuery.getIsRead()
        );
        
        return ResponseData.success(voPage.getRecords().stream()
                .sorted((o1, o2) -> {
                    // 未读的排前面
                    if (o1.getIsRead().equals("是")) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .collect(Collectors.toList()));
    }
    
    /**
     * 新增通知
     * @param noticeDTO 通知信息
     * @return 是否新增成功
     */
    @PostMapping
    @Operation(summary = "新增通知", description = "新增通知", method = "POST")
    public ResponseData add(@RequestBody NoticeDTO noticeDTO) {
        log.info("新增通知: {}", noticeDTO);
        
        try {
            Notice notice = BeanUtil.copyProperties(noticeDTO, Notice.class);
            boolean success = iNoticeService.save(notice);
            
            if (success) {
                return ResponseData.success();
            } else {
                return ResponseData.error("新增通知失败");
            }
        } catch (Exception e) {
            log.error("新增通知失败", e);
            return ResponseData.error("新增通知失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除通知
     * @param id 通知id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除通知", description = "删除通知", method = "DELETE")
    public ResponseData delete(@PathVariable Long id) {
        log.info("删除通知: {}", id);
        
        try {
            boolean success = iNoticeService.removeById(id);
            
            if (success) {
                return ResponseData.success();
            } else {
                return ResponseData.error("删除通知失败");
            }
        } catch (Exception e) {
            log.error("删除通知失败", e);
            return ResponseData.error("删除通知失败：" + e.getMessage());
        }
    }
    
    /**
     * 修改通知
     * @param noticeDTO 通知信息
     * @return 是否修改成功
     */
    @PutMapping
    @Operation(summary = "修改通知", description = "修改通知", method = "PUT")
    public ResponseData update(@RequestBody NoticeDTO noticeDTO) {
        log.info("修改通知: {}", noticeDTO);
        
        try {
            Notice notice = BeanUtil.copyProperties(noticeDTO, Notice.class);
            boolean success = iNoticeService.updateById(notice);
            
            if (success) {
                return ResponseData.success();
            } else {
                return ResponseData.error("修改通知失败");
            }
        } catch (Exception e) {
            log.error("修改通知失败", e);
            return ResponseData.error("修改通知失败：" + e.getMessage());
        }
    }
    
    /**
     * 添加通知已读记录
     * @param noticeRead 通知
     * @return 是否添加成功
     */
    @PostMapping("/read")
    @Operation(summary = "添加通知已读记录", description = "添加通知已读记录", method = "POST")
    public ResponseData addNoticeRead(@RequestBody NoticeRead noticeRead) {
        log.info("添加通知已读记录: {}", noticeRead);
        
        try {
            boolean success = iNoticeReadService.addNoticeRead(noticeRead);
            
            if (success) {
                return ResponseData.success();
            } else {
                return ResponseData.error("添加通知已读记录失败");
            }
        } catch (Exception e) {
            log.error("添加通知已读记录失败", e);
            return ResponseData.error("添加通知已读记录失败：" + e.getMessage());
        }
    }

    /**
     * 撤回
     * @param id 通知id
     * @return 是否添加成功
     */
    @PostMapping("/{id}")
    @Operation(summary = "撤回", description = "撤回", method = "POST")
    public ResponseData withdraw(@PathVariable Long id) {
        log.info("撤回: {}", id);

        iNoticeService.update(new LambdaUpdateWrapper<Notice>()
                .set(Notice::getReceiverId, -1)
                .eq(Notice::getId, id));

        return ResponseData.success();
    }

    @GetMapping("/list")
    @Operation(summary = "获取通知列表", description = "获取通知列表", method = "GET")
    public ResponseData<List<NoticeVO>> listNotices(NoticeQuery query) {
        return ResponseData.success(BeanUtil.copyToList(iNoticeService.list(query.toPage(), new LambdaQueryWrapper<Notice>()
                .like(StringUtils.isNotEmpty(query.getTitle()), Notice::getTitle, query.getTitle())
                .like(StringUtils.isNotEmpty(query.getContext()), Notice::getContext, query.getContext())
                .eq(StringUtils.isNotEmpty(query.getType()), Notice::getType, query.getType())), NoticeVO.class));
    }
}