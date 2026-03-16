package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bird_forum.domain.dto.ArticleDTO;
import com.bird_forum.domain.po.Article;
import com.bird_forum.domain.po.ArticleCategory;
import com.bird_forum.domain.po.ArticleLikeCollection;
import com.bird_forum.domain.po.ArticleViews;
import com.bird_forum.domain.query.ArticleQuery;
import com.bird_forum.domain.vo.ArticleVO;
import com.bird_forum.mapper.ArticleMapper;
import com.bird_forum.mapper.ArticleCategoryMapper;
import com.bird_forum.service.IArticleCategoryService;
import com.bird_forum.service.IArticleLikeCollectionService;
import com.bird_forum.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.service.IArticleViewsService;
import com.bird_forum.util.MinioUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private IArticleCategoryService iArticleCategoryService;

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Resource
    private IArticleLikeCollectionService iArticleLikeCollectionService;

    @Resource
    private IArticleViewsService iArticleViewsService;

    /**
     * 获取文章列表
     *
     * @param articleQuery 查询参数
     * @return 文章列表
     */
    @Override
    public List<ArticleVO> listArticles(ArticleQuery articleQuery) {
        // TODO 分页查询
        List<ArticleVO> articles = articleMapper.listArticles(articleQuery);
        // 设置图片和作者头像的url
        articles.forEach(item -> {
            item.setImage(MinioUtils.getFileUrl(item.getImage()));
            item.setAvatar(MinioUtils.getFileUrl(item.getAvatar()));
            setData(item);
        });

        return articles;
    }

    /**
     * 删除文章
     *
     * @param id 文章id
     * @return 是否删除成功
     */
    @Override
    public boolean deleteById(Long id) {
        // 删除文章
        Boolean r1 = articleMapper.deleteById(id) > 0;
        // 删除文章和分类关系
        Boolean r2 = iArticleCategoryService.lambdaUpdate()
                .eq(ArticleCategory::getArticleId, id)
                .remove();

        return r1 && r2;
    }

    /**
     * 获取热门文章列表
     *
     * @return 热门文章列表
     */
    @Override
    public List<ArticleVO> listHotArticleList() {
        List<ArticleVO> articles = articleMapper.listHotArticleList();

        articles.forEach(item -> {
            item.setImage(MinioUtils.getFileUrl(item.getImage()));
            item.setAvatar(MinioUtils.getFileUrl(item.getAvatar()));
            setData(item);
        });

        return articles;
    }

    @Override
    public boolean saveArticle(ArticleDTO articleDTO) {
        MultipartFile image = articleDTO.getImageFile();
        String imageUrl = "";

        try {
            // 将图片上传到MinIO
            imageUrl = MinioUtils.uploadFile(image.getInputStream(), "/images/" + image.getResource().getFilename());
        } catch (Exception e) {
            throw new RuntimeException("上传图片失败: " + e);
        }

        Article article = BeanUtil.copyProperties(articleDTO, Article.class);
        article.setImage(imageUrl);

        // 保存文章
        save(article);

        // 保存文章和分类关系
        List<ArticleCategory> articleCategoryList = Arrays.stream(articleDTO.getCategories()).map(item -> {
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(article.getId());
            articleCategory.setCategoryId(item);

            return articleCategory;
        }).collect(Collectors.toList());
        // 批量保存文章与分类的关系
        iArticleCategoryService.saveBatch(articleCategoryList);

        return true;
    }

    /**
     * 根据id获取文章
     *
     * @param id 文章id
     * @return 文章
     */
    @Override
    public ArticleVO getById(Long id) {
        ArticleVO articleVO = articleMapper.getById(id);

        articleVO.setAvatar(MinioUtils.getFileUrl(articleVO.getAvatar()));
        articleVO.setImage(MinioUtils.getFileUrl(articleVO.getImage()));
        setData(articleVO);

        return articleVO;
    }

    private void setData(ArticleVO articleVO) {
        articleVO.setCollectNum(iArticleLikeCollectionService.count(new LambdaQueryWrapper<ArticleLikeCollection>()
                .eq(ArticleLikeCollection::getArticleId, articleVO.getId())
                .eq(ArticleLikeCollection::getType, 2)));
        ArticleViews articleViews = iArticleViewsService.getOne(new LambdaQueryWrapper<ArticleViews>()
                .eq(ArticleViews::getArticleId, articleVO.getId()));

        if (ObjectUtil.isNotNull(articleViews)) {
            articleVO.setViewNum(articleViews.getViewCount());
        }

        articleVO.setCategories(articleCategoryMapper.listCategoriesByArticleId(articleVO.getId()));
    }

    /**
     * 获取用户发布的文章列表
     *
     * @param userId 用户id
     * @return 文章列表
     */
    @Override
    public List<ArticleVO> listByPublisher(Long userId) {
        List<ArticleVO> articles = articleMapper.listByPublisher(userId);
        articles.forEach(item -> {
            item.setImage(MinioUtils.getFileUrl(item.getImage()));
            item.setAvatar(MinioUtils.getFileUrl(item.getAvatar()));
            setData(item);
        });
        return articles;
    }

    /**
     * 获取用户收藏的文章列表
     *
     * @param userId 用户id
     * @return 文章列表
     */
    @Override
    public List<ArticleVO> listCollected(Long userId) {
        List<ArticleVO> articles = articleMapper.listCollected(userId);
        articles.forEach(item -> {
            item.setImage(MinioUtils.getFileUrl(item.getImage()));
            item.setAvatar(MinioUtils.getFileUrl(item.getAvatar()));
            setData(item);
        });
        return articles;
    }
}
