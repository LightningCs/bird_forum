package com.bird_forum.controller;


import cn.hutool.core.bean.BeanUtil;
import com.bird_forum.context.ThreadContext;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.ReportDTO;
import com.bird_forum.domain.po.Report;
import com.bird_forum.domain.query.ReportQuery;
import com.bird_forum.domain.vo.ReportVO;
import com.bird_forum.service.IReportService;
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
@RequestMapping("/report")
@Tag(name = "举报模块")
@Slf4j
public class ReportController {
    @Resource
    private IReportService iReportService;

    /**
     * 添加举报
     *
     * @param reportDTO 举报DTO
     * @return 响应数据
     */
    @PostMapping
    @Operation(summary = "添加举报", description = "添加举报", method = "POST")
    public ResponseData add(@RequestBody ReportDTO reportDTO) {
        log.info("添加举报:{}", reportDTO);

        // 添加举报成功
        if (iReportService.save(BeanUtil.copyProperties(reportDTO, Report.class))) {
            return ResponseData.success();
        }

        // 添加举报失败
        return ResponseData.error();
    }

    /**
     * 删除举报
     *
     * @param id 举报id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除举报", description = "删除举报", method = "DELETE")
    public ResponseData delete(@PathVariable Long id) {
        log.info("删除举报:{}", id);

        // 删除举报成功
        if (iReportService.removeById(id)) {
            return ResponseData.success();
        }

        // 删除举报失败
        return ResponseData.error();
    }

    /**
     * 批量删除举报
     *
     * @param ids 举报id集合
     * @return 响应数据
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除举报", description = "批量删除举报", method = "DELETE")
    public ResponseData deleteBatch(@RequestBody List<Long> ids) {
        log.info("批量删除举报:{}", ids);

        // 批量删除举报成功
        if (iReportService.removeByIds(ids)) {
            return ResponseData.success();
        }

        // 批量删除举报失败
        return ResponseData.error();
    }

    /**
     * 更新举报
     *
     * @param reportDTO 举报DTO
     * @return 响应数据
     */
    @PutMapping
    @Operation(summary = "更新举报", description = "更新举报", method = "PUT")
    public ResponseData update(@RequestBody ReportDTO reportDTO) {
        log.info("更新举报:{}", reportDTO);

        // 更新举报成功
        if (iReportService.updateById(BeanUtil.copyProperties(reportDTO, Report.class))) {
            return ResponseData.success();
        }

        // 更新举报失败
        return ResponseData.error();
    }

    /**
     * 查询举报
     *
     * @param id 举报id
     * @return 响应数据
     */
    @GetMapping
    @Operation(summary = "查询举报", description = "查询举报", method = "GET")
    public ResponseData<ReportVO> list(Long id) {
        log.info("查询举报: {}", id);

        return ResponseData.success(BeanUtil.copyProperties(iReportService.getById(id), ReportVO.class));
    }

    /**
     * 分页查询举报
     *
     * @param reportQuery 分页查询参数
     * @return 响应数据
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询举报", description = "分页查询举报", method = "GET")
    public ResponseData<List<ReportVO>> list(ReportQuery reportQuery) {
        log.info("分页查询举报:{}", reportQuery);

        return ResponseData.success(iReportService.list(reportQuery));
    }

    @PostMapping("/{id}")
    @Operation(summary = "处理举报", description = "处理举报", method = "POST")
    public ResponseData handle(@PathVariable Long id, Boolean yes, String remark) {
        log.info("处理举报:{}", id);

        if (iReportService.handle(id, yes, remark)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    @PostMapping("/ai/{id}")
    @Operation(summary = "ai处理举报", description = "ai处理举报", method = "POST")
    public ResponseData aiHandle(@PathVariable Long id) {
        log.info("处理举报:{}", id);

        if (iReportService.handle(id)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }
}
