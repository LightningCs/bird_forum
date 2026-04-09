package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.History;
import com.bird_forum.domain.query.HistoryQuery;
import com.bird_forum.domain.vo.HistoryVO;
import com.bird_forum.mapper.HistoryMapper;
import com.bird_forum.service.IArticleService;
import com.bird_forum.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.BeanUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
@RequiredArgsConstructor
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

    @Resource
    private IArticleService iArticleService;

    /**
     * 获取历史记录
     * @return 历史记录
     */
    @Override
    public List<HistoryVO> listHistory(HistoryQuery query) {
        List<HistoryVO> histories = BeanUtil.copyToList(list(new LambdaQueryWrapper<History>()
                .eq(History::getUserId, query.getUserId())), HistoryVO.class);

        histories.forEach(hisStory -> {
            hisStory.setArticle(iArticleService.getById(hisStory.getArticleId()));
        });

        return histories;
    }

    /**
     * 更新历史记录
     * @param articleId 文章id
     * @param userId 用户id
     * @return 是否更新成功
     */
    @Override
    public Boolean updateHistory(Long articleId, Long userId) {
        History history = lambdaQuery()
                .eq(History::getArticleId, articleId)
                .eq(History::getUserId, userId)
                .one();

        // 如果是第一次浏览该文章
        if (BeanUtils.isNull(history)) {
            return save(History.builder()
                    .articleId(articleId)
                    .userId(userId)
                    .viewTime(LocalDateTime.now())
                    .build());
        }

        // 更新为最新时间
        return lambdaUpdate()
                .set(History::getViewTime, LocalDateTime.now())
                .eq(History::getArticleId, articleId)
                .eq(History::getUserId, userId)
                .update();
    }
}
