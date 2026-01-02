package com.bird_forum.controller;


import cn.hutool.core.bean.BeanUtil;
import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.ReportReasonDTO;
import com.bird_forum.domain.po.ReportReason;
import com.bird_forum.domain.query.ReportReasonQuery;
import com.bird_forum.domain.vo.ReportReasonVO;
import com.bird_forum.service.IReportReasonService;
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
@RequestMapping("/report-reason")
@Tag(name = "举报理由模块")
@Slf4j
public class ReportReasonController {
    @Resource
    private IReportReasonService iReportReasonService;

    /**
     * 添加举报理由
     * @param reportReasonDTO 举报理由DTO
     * @return 响应数据
     */
    @PostMapping
    @Operation(summary = "添加举报理由", description = "添加举报理由", method = "POST")
    public ResponseData add(ReportReasonDTO reportReasonDTO) {
        log.info("添加举报理由: {}", reportReasonDTO);

        if (iReportReasonService.save(BeanUtil.copyProperties(reportReasonDTO, ReportReason.class))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 删除举报理由
     * @param id 举报理由id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除举报理由", description = "删除举报理由", method = "DELETE")
    public ResponseData delete(@PathVariable Long id) {
        log.info("删除举报理由: {}", id);

        if (iReportReasonService.removeById(id)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 批量删除举报理由
     * @param ids 举报理由id列表
     * @return 响应数据
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除举报理由", description = "批量删除举报理由", method = "DELETE")
    public ResponseData deleteBatch(@RequestBody List<Long> ids) {
        log.info("批量删除举报理由: {}", ids);

        if (iReportReasonService.removeByIds(ids)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 修改举报理由
     * @param reportReasonDTO 举报理由DTO
     * @return 响应数据
     */
    @PutMapping
    @Operation(summary = "修改举报理由", description = "修改举报理由", method = "PUT")
    public ResponseData update(ReportReasonDTO reportReasonDTO) {
        log.info("修改举报理由: {}", reportReasonDTO);

        if (iReportReasonService.updateById(BeanUtil.copyProperties(reportReasonDTO, ReportReason.class))) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 获取举报理由
     * @param id 举报理由id
     * @return 响应数据
     */
    @GetMapping
    @Operation(summary = "获取举报理由", description = "获取举报理由", method = "GET")
    public ResponseData<ReportReasonVO> get(Long id) {
        log.info("获取举报理由: {}", id);

        return ResponseData.success(BeanUtil.copyProperties(iReportReasonService.getById(id), ReportReasonVO.class));
    }

    /**
     * 获取举报理由列表
     * @param reportReasonQuery 举报理由查询参数
     * @return 响应数据
     */
    @GetMapping("/list")
    @Operation(summary = "获取举报理由列表", description = "获取举报理由列表", method = "GET")
    public ResponseData<List<ReportReasonVO>> list(ReportReasonQuery reportReasonQuery) {
        return ResponseData.success(BeanUtil.copyToList(iReportReasonService.list(reportReasonQuery), ReportReasonVO.class));
    }
}
