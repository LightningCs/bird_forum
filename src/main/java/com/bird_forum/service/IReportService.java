package com.bird_forum.service;

import com.bird_forum.domain.po.Report;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.ReportQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IReportService extends IService<Report> {
    /**
     * 获取举报集合
     * @param reportQuery 分页查询参数
     * @return 举报集合
     */
    List<Report> list(ReportQuery reportQuery);
}
