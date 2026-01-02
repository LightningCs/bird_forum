package com.bird_forum.controller;


import cn.hutool.core.bean.BeanUtil;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.MessageDTO;
import com.bird_forum.domain.po.Message;
import com.bird_forum.domain.query.MessageQuery;
import com.bird_forum.domain.vo.MessageVO;
import com.bird_forum.service.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/message")
@Tag(name = "消息模块")
@Slf4j
public class MessageController {
    @Resource
    private IMessageService iMessageService;

    /**
     * 发送消息
     * @param messageDTO 消息DTO
     * @return 响应数据
     */
    @PostMapping
    @Operation(summary = "发送消息", description = "发送消息", method = "POST")
    public ResponseData sendMessage(@RequestBody MessageDTO messageDTO) {
        log.info("发送消息: {}", messageDTO);

        if (iMessageService.save(BeanUtil.copyProperties(messageDTO, Message.class))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 删除消息
     * @param id 消息id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除消息", description = "删除消息", method = "DELETE")
    public ResponseData delete(@PathVariable Long id) {
        log.info("删除消息: {}", id);

        if (iMessageService.removeById(id)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 批量删除消息
     * @param messageIds 消息id列表
     * @return 响应数据
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除消息", description = "批量删除消息", method = "DELETE")
    public ResponseData deleteBatch(@RequestBody List<Long> messageIds) {
        log.info("批量删除消息: {}", messageIds);

        if (iMessageService.removeByIds(messageIds)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 修改消息
     * @param messageDTO 消息DTO
     * @return 响应数据
     */
    @PutMapping
    @Operation(summary = "修改消息", description = "修改消息", method = "PUT")
    public ResponseData update(@RequestBody MessageDTO messageDTO) {
        log.info("更新消息: {}", messageDTO);

        if (iMessageService.updateById(BeanUtil.copyProperties(messageDTO, Message.class))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 获取消息
     * @param messageId 消息id
     * @return 响应数据
     */
    @GetMapping
    @Operation(summary = "获取消息", description = "获取消息", method = "GET")
    public ResponseData<MessageVO> get(Long messageId) {
        log.info("获取消息: {}", messageId);

        return ResponseData.success(BeanUtil.copyProperties(iMessageService.getById(messageId), MessageVO.class));
    }

    /**
     * 获取消息列表
     * @param messageQuery 消息查询参数
     * @return 响应数据
     */
    @GetMapping("/list")
    @Operation(summary = "获取消息列表", description = "获取消息列表", method = "GET")
    public ResponseData<List<MessageVO>> list(MessageQuery messageQuery) {
        log.info("获取消息列表: {}", messageQuery);

        return ResponseData.success(BeanUtil.copyToList(iMessageService.list(messageQuery), MessageVO.class));
    }
}
