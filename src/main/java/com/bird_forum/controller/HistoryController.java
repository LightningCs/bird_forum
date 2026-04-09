package com.bird_forum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.HistoryDTO;
import com.bird_forum.domain.po.History;
import com.bird_forum.domain.query.HistoryQuery;
import com.bird_forum.domain.query.PageQuery;
import com.bird_forum.domain.vo.HistoryVO;
import com.bird_forum.service.IHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/history")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "历史记录模块")
public class HistoryController {
    public final IHistoryService historyService;

    /**
     * 获取历史记录
     * @return histories
     */
    @GetMapping
    @Operation(summary = "获取历史记录", description = "获取历史记录", method = "GET")
    public ResponseData<List<HistoryVO>> listHistory(HistoryQuery query) {
        log.info("获取历史记录");

        return ResponseData.success(historyService.listHistory(query));
    }
    
    /**
     * 新增或更新历史记录
     * @param historyDTO 历史记录
     * @return 是否成功
     */
    @PostMapping
    @Operation(summary = "新增或更新历史记录", description = "新增或更新历史记录，如果记录存在则更新时间，否则新增", method = "POST")
    public ResponseData upsertHistory(@RequestBody HistoryDTO historyDTO) {
        log.info("新增或更新历史记录，articleId: {}, userId: {}", historyDTO.getArticleId(), historyDTO.getUserId());
        
        try {
            Boolean success = historyService.updateHistory(historyDTO.getArticleId(), historyDTO.getUserId());
            
            if (success) {
                return ResponseData.success();
            } else {
                return ResponseData.error("更新历史记录失败");
            }
        } catch (Exception e) {
            log.error("更新历史记录失败", e);
            return ResponseData.error("更新历史记录失败：" + e.getMessage());
        }
    }

    /**
     * 删除历史记录
     * @param history 历史记录
     * @return 是否成功
     */
    @DeleteMapping()
    @Operation(summary = "删除历史记录", description = "删除历史记录", method = "DELETE")
    public ResponseData deleteHistory(@RequestBody History history) {
        log.info("删除历史记录，history: {}", history);

        try {
            Boolean success = historyService.remove(new LambdaQueryWrapper<History>()
                    .eq(History::getArticleId, history.getArticleId())
                    .eq(History::getUserId, history.getUserId()));

            if (success) {
                return ResponseData.success();
            } else {
                return ResponseData.error("删除历史记录失败");
            }
        } catch (Exception e) {
            log.error("删除历史记录失败", e);
            return ResponseData.error("删除历史记录失败：" + e.getMessage());
        }
    }
}
