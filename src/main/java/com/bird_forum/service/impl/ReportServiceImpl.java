package com.bird_forum.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bird_forum.domain.po.*;
import com.bird_forum.domain.query.ReportQuery;
import com.bird_forum.domain.vo.ArticleVO;
import com.bird_forum.domain.vo.CommentVO;
import com.bird_forum.domain.vo.ReportVO;
import com.bird_forum.mapper.ReportMapper;
import com.bird_forum.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Resource
    private IReportReasonService iReportReasonService;

    @Resource
    private INoticeService iNoticeService;

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
        ReportReason reportReason = iReportReasonService.getById(one.getReasonId());
        Notice notice = new Notice();

        switch (one.getTargetType()) {
            case "用户":
                iUserService.update(new LambdaUpdateWrapper<User>()
                        .set(User::getStatus, yes ? "禁用" : "启用")
                        .eq(User::getId, one.getTargetId()));

                // 若审核通过
                if (yes) {
                    notice = new Notice();

                    notice.setTitle("关于您的账号被举报的处理通知");
                    notice.setType("系统通知");
                    notice.setContext("尊敬的用户，您好！我们收到社区用户的举报，并经管理员核实，" +
                            "您的账号违反了社区规范中关于" + reportReason.getContext() +
                            "的规定。根据平台管理规定，我们已对该内容进行了屏蔽处理。" +
                            "请您在后续的互动中遵守社区公约，共同维护良好的交流环境。");
                    notice.setReceiverId(one.getTargetId());
                }

                break;
            case "文章":
                iArticleService.update(new LambdaUpdateWrapper<Article>()
                        .set(Article::getIsIllegal, yes ? 1 : 0)
                        .eq(Article::getId, one.getTargetId()));

                // 若审核通过
                if (yes) {
                    notice = new Notice();
                    ArticleVO articleVO = iArticleService.getById(one.getTargetId());

                    notice.setTitle("关于您发布的内容被举报的处理通知");
                    notice.setType("系统通知");
                    notice.setContext("尊敬的用户，您好！我们收到社区用户的举报，并经管理员核实，" +
                            "您发布的内容违反了社区规范中关于" + reportReason.getContext() +
                            "的规定。根据平台管理规定，我们已对该内容进行了屏蔽处理。" +
                            "请您在后续的互动中遵守社区公约，共同维护良好的交流环境。");
                    notice.setReceiverId(articleVO.getPublisherId());
                }
                break;
            case "评论":
                iCommentService.update(new LambdaUpdateWrapper<Comment>()
                        .set(Comment::getIsIllegal, yes ? 1 : 0)
                        .eq(Comment::getId, one.getTargetId()));

                // 若审核通过
                if (yes) {
                    notice = new Notice();
                    Comment comment = iCommentService.getById(one.getTargetId());

                    notice.setTitle("关于您发布的内容被举报的处理通知");
                    notice.setType("系统通知");
                    notice.setContext("尊敬的用户，您好！我们收到社区用户的举报，并经管理员核实，" +
                            "您发布的内容违反了社区规范中关于" + reportReason.getContext() +
                            "的规定。根据平台管理规定，我们已对该内容进行了屏蔽处理。" +
                            "请您在后续的互动中遵守社区公约，共同维护良好的交流环境。");
                    notice.setReceiverId(comment.getCommentUserId());
                }

                break;
        }

        // 发送通知给举报用户
        Notice n1 = new Notice();
        n1.setTitle("关于您的举报处理结果通知");
        n1.setType("系统通知");
        n1.setContext("尊敬的用户，您好！我们已收到您的举报，经管理员核实，" +
                "您的举报内容已经处理完毕，结果是: " + (yes ? "通过" : "未通过"));
        n1.setReceiverId(one.getReporterId());

        if (ObjectUtil.isNotNull(notice)) {
            iNoticeService.save(notice);
        }

        iNoticeService.save(n1);

        return update(new LambdaUpdateWrapper<Report>()
                .set(Report::getStatus, "已处理")
                .set(Report::getResult, yes ? 1 : 0)
                .set(Report::getRemark, remark)
                .eq(Report::getId, id));
    }

    @Override
    public boolean handle(Long id) {
        Report one = getById(id);
        ReportReason reportReason = iReportReasonService.getById(one.getReasonId());
        boolean yes = ModelUtils.chat(one.getContext(), Boolean.class);

        Notice notice = null;

        switch (one.getTargetType()) {
            case "用户":
                iUserService.update(new LambdaUpdateWrapper<User>()
                        .set(User::getStatus, yes ? "禁用" : "启用")
                        .eq(User::getId, one.getTargetId()));

                // 若审核通过
                if (yes) {
                    notice = new Notice();

                    notice.setTitle("关于您的账号被举报的处理通知");
                    notice.setType("系统通知");
                    notice.setContext("尊敬的用户，您好！我们收到社区用户的举报，并经管理员核实，" +
                            "您的账号违反了社区规范中关于" + reportReason.getContext() +
                            "的规定。根据平台管理规定，我们已对该内容进行了屏蔽处理。" +
                            "请您在后续的互动中遵守社区公约，共同维护良好的交流环境。");
                    notice.setReceiverId(one.getTargetId());
                }

                break;
            case "文章":
                iArticleService.update(new LambdaUpdateWrapper<Article>()
                        .set(Article::getIsIllegal, yes ? 1 : 0)
                        .eq(Article::getId, one.getTargetId()));

                // 若审核通过
                if (yes) {
                    notice = new Notice();
                    ArticleVO articleVO = iArticleService.getById(one.getTargetId());

                    notice.setTitle("关于您发布的内容被举报的处理通知");
                    notice.setType("系统通知");
                    notice.setContext("尊敬的用户，您好！我们收到社区用户的举报，并经管理员核实，" +
                            "您发布的内容违反了社区规范中关于" + reportReason.getContext() +
                            "的规定。根据平台管理规定，我们已对该内容进行了屏蔽处理。" +
                            "请您在后续的互动中遵守社区公约，共同维护良好的交流环境。");
                    notice.setReceiverId(articleVO.getPublisherId());
                }
                break;
            case "评论":
                iCommentService.update(new LambdaUpdateWrapper<Comment>()
                        .set(Comment::getIsIllegal, yes ? 1 : 0)
                        .eq(Comment::getId, one.getTargetId()));

                // 若审核通过
                if (yes) {
                    notice = new Notice();
                    Comment comment = iCommentService.getById(one.getTargetId());

                    notice.setTitle("关于您发布的内容被举报的处理通知");
                    notice.setType("系统通知");
                    notice.setContext("尊敬的用户，您好！我们收到社区用户的举报，并经管理员核实，" +
                            "您发布的内容违反了社区规范中关于" + reportReason.getContext() +
                            "的规定。根据平台管理规定，我们已对该内容进行了屏蔽处理。" +
                            "请您在后续的互动中遵守社区公约，共同维护良好的交流环境。");
                    notice.setReceiverId(comment.getCommentUserId());
                }

                break;
        }

        // 发送通知给举报用户
        Notice n1 = new Notice();
        n1.setTitle("关于您的举报处理结果通知");
        n1.setType("系统通知");
        n1.setContext("尊敬的用户，您好！我们已收到您的举报，经管理员核实，" +
                "您的举报内容已经处理完毕，结果是: " + (yes ? "通过" : "未通过"));
        n1.setReceiverId(one.getReporterId());

        if (ObjectUtil.isNotNull(notice)) {
            iNoticeService.save(notice);
        }

        iNoticeService.save(n1);

        return update(new LambdaUpdateWrapper<Report>()
                .set(Report::getStatus, "已处理")
                .set(Report::getResult, yes ? 1 : 0)
                .eq(Report::getId, id));
    }
}
