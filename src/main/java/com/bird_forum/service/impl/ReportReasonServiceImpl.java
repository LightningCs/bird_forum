package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.domain.po.ReportReason;
import com.bird_forum.domain.query.ReportReasonQuery;
import com.bird_forum.mapper.ReportReasonMapper;
import com.bird_forum.service.IReportReasonService;
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
public class ReportReasonServiceImpl extends ServiceImpl<ReportReasonMapper, ReportReason> implements IReportReasonService {

    /**
     * 获取举报理由列表
     * @param reportReasonQuery 查询参数
     * @return 列表
     */
    @Override
    public List<ReportReason> list(ReportReasonQuery reportReasonQuery) {
        return lambdaQuery()
                .like(BeanUtils.isNotNull(reportReasonQuery.getContext()), ReportReason::getContext, reportReasonQuery.getContext())
                .eq(BeanUtils.isNotNull(reportReasonQuery.getStatus()), ReportReason::getStatus, reportReasonQuery.getStatus())
                .list(reportReasonQuery.toPage());
    }
}
