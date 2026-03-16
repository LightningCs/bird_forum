package com.bird_forum.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.domain.dto.CategoryDTO;
import com.bird_forum.domain.po.ArticleCategory;
import com.bird_forum.domain.po.Category;
import com.bird_forum.domain.query.CategoryQuery;
import com.bird_forum.domain.vo.CategoryVO;
import com.bird_forum.mapper.CategoryMapper;
import com.bird_forum.service.IArticleCategoryService;
import com.bird_forum.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bird_forum.util.BeanUtils;
import com.bird_forum.util.MinioUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    private IArticleCategoryService articleCategoryService;

    /**
     * 添加分类
     *
     * @param categoryDTO 分类参数
     * @return 是否成功
     */
    @Override
    public boolean addCategory(CategoryDTO categoryDTO) {
        return save(new Category(categoryDTO.getName()));
    }

    /**
     * 更新分类
     *
     * @param categoryDTO 分类参数
     * @return 是否成功
     */
    @Override
    public boolean update(CategoryDTO categoryDTO) {
        return lambdaUpdate()
                .set(Category::getName, categoryDTO.getName())
                .set(Category::getStatus, categoryDTO.getStatus())
                .eq(Category::getId, categoryDTO.getId())
                .update();
    }

    /**
     * 获取分类集合
     *
     * @param categoryQuery 分页查询参数
     * @return 分类集合
     */
    @Override
    public List<CategoryVO> list(CategoryQuery categoryQuery) {
        List<Category> categoryList = lambdaQuery()
                .like(BeanUtils.isNotNull(categoryQuery.getName()), Category::getName, categoryQuery.getName())
                .eq(BeanUtils.isNotNull(categoryQuery.getStatus()), Category::getStatus, categoryQuery.getStatus())
                .list(categoryQuery.toPage());

        List<CategoryVO> categoryVOList = BeanUtil.copyToList(categoryList, CategoryVO.class);

        categoryVOList.forEach(item -> {
            item.setIcon(MinioUtils.getFileUrl(item.getIcon()));
            // 获取文章数量
            item.setArticleCount(articleCategoryService.count(new LambdaQueryWrapper<ArticleCategory>().eq(ArticleCategory::getCategoryId, item.getId())));
        });

        return categoryVOList;
    }

    /**
     * 获取分类
     *
     * @param articleId 文章id
     * @return 分类集合
     */
    @Override
    public List<CategoryVO> list(Long articleId) {
        // 获取文章分类 id
        List<ArticleCategory> categoryList = articleCategoryService.list(new LambdaQueryWrapper<ArticleCategory>()
                .select(ArticleCategory::getCategoryId)
                .eq(ArticleCategory::getArticleId, articleId));

        return BeanUtil.copyToList(list(lambdaQuery()
                .in(Category::getId, categoryList.stream()
                        .map(ArticleCategory::getCategoryId)
                        .collect(Collectors.toList()))), CategoryVO.class);
    }
}
