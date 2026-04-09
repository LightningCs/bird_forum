package com.bird_forum.mapper;

import com.bird_forum.domain.po.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.query.ReportQuery;
import com.bird_forum.domain.vo.ReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {

    List<ReportVO> list(ReportQuery reportQuery);
}
