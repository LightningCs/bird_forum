package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.domain.po.Report;
import com.bird_forum.domain.query.ReportQuery;
import com.bird_forum.mapper.ReportMapper;
import com.bird_forum.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

    /**
     * 获取举报集合
     * @param reportQuery 分页查询参数
     * @return 举报集合
     */
    @Override
    public List<Report> list(ReportQuery reportQuery) {
        return lambdaQuery()
                .eq(BeanUtils.isNotNull(reportQuery.getReporterId()), Report::getReporterId, reportQuery.getReporterId())
                .eq(BeanUtils.isNotNull(reportQuery.getReasonId()), Report::getReasonId, reportQuery.getReasonId())
                .like(BeanUtils.isNotNull(reportQuery.getContext()), Report::getContext, reportQuery.getContext())
                .eq(BeanUtils.isNotNull(reportQuery.getStatus()), Report::getStatus, reportQuery.getStatus())
                .list(reportQuery.toPage());
    }
}
