package com.bird_forum.mapper;

import com.bird_forum.domain.po.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.vo.HistoryVO;
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
public interface HistoryMapper extends BaseMapper<History> {

    List<HistoryVO> listHistory(Long id);
}
