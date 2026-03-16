package com.bird_forum.controller;


import com.bird_forum.domain.query.PageQuery;
import com.bird_forum.domain.vo.HistoryVO;
import com.bird_forum.service.IHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public List<HistoryVO> listHistory(PageQuery query) {
        log.info("获取历史记录");

        // TODO 分页查询
        return historyService.listHistory(query);
    }
}
