package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.Comment;
import com.bird_forum.domain.po.Report;
import com.bird_forum.domain.po.User;
import com.bird_forum.domain.query.ReportQuery;
import com.bird_forum.domain.vo.ReportVO;
import com.bird_forum.mapper.ReportMapper;
import com.bird_forum.service.IArticleService;
import com.bird_forum.service.ICommentService;
import com.bird_forum.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.service.IUserService;
import com.bird_forum.util.ModelUtils;
import jakarta.annotation.Resource;
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

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private IUserService iUserService;

    @Resource
    private IArticleService iArticleService;

    @Resource
    private ICommentService iCommentService;

    /**
     * 获取举报集合
     * @param reportQuery 分页查询参数
     * @return 举报集合
     */
    @Override
    public List<ReportVO> list(ReportQuery reportQuery) {
        List<ReportVO> resList = reportMapper.list(reportQuery);

        return resList.subList((reportQuery.getPageNo() - 1) * reportQuery.getPageSize(), Math.min(reportQuery.getPageNo() * reportQuery.getPageSize(), resList.size()));
    }

    @Override
    public boolean handle(Long id, Boolean yes, String remark) {
        Report one = getById(id);

        switch (one.getTargetType()) {
            case "用户":
                iUserService.update(new LambdaUpdateWrapper<User>()
                        .set(User::getStatus, yes ? "禁用" : "启用")
                        .eq(User::getId, id));
                break;
            case "文章":
                iArticleService.update(new LambdaUpdateWrapper<Article>()
                        .set(Article::getIsIllegal, yes ? 1 : 0)
                        .eq(Article::getId, id));
                break;
            case "评论":
                iCommentService.update(new LambdaUpdateWrapper<Comment>()
                        .set(Comment::getIsIllegal, yes ? 1 : 0)
                        .eq(Comment::getId, id));
                break;
        }

        return update(new LambdaUpdateWrapper<Report>()
                .set(Report::getStatus, "已处理")
                .set(Report::getRemark, remark)
                .eq(Report::getId, id));
    }

    @Override
    public boolean handle(Long id) {
        Report one = getById(id);

        boolean yes = ModelUtils.chat(one.getContext(), Boolean.class);

        switch (one.getTargetType()) {
            case "用户":
                iUserService.update(new LambdaUpdateWrapper<User>()
                        .set(User::getStatus, yes ? "禁用" : "启用")
                        .eq(User::getId, one.getTargetId()));
                break;
            case "文章":
                iArticleService.update(new LambdaUpdateWrapper<Article>()
                        .set(Article::getIsIllegal, yes ? 1 : 0)
                        .eq(Article::getId, one.getTargetId()));
                break;
            case "评论":
                iCommentService.update(new LambdaUpdateWrapper<Comment>()
                        .set(Comment::getIsIllegal, yes ? 1 : 0)
                        .eq(Comment::getId, one.getTargetId()));
                break;
        }

        return update(new LambdaUpdateWrapper<Report>()
                .set(Report::getStatus, "已处理")
                .eq(Report::getId, id));
    }
}
