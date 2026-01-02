package com.bird_forum.service;

import com.bird_forum.domain.po.History;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.vo.HistoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface IHistoryService extends IService<History> {

    /**
     * 获取历史记录
     * @return 历史记录
     */
    List<HistoryVO> listHistory();

    Boolean updateHistory(Long articleId, Long userId);
}
