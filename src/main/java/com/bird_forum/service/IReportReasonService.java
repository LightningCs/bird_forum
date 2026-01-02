package com.bird_forum.service;

import com.bird_forum.domain.po.ReportReason;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.ReportReasonQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IReportReasonService extends IService<ReportReason> {
    /**
     * 获取举报理由列表
     * @param reportReasonQuery 举报理由查询参数
     * @return 举报理由列表
     */
    List<ReportReason> list(ReportReasonQuery reportReasonQuery);
}
