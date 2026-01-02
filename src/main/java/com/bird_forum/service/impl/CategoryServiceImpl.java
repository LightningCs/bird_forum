package com.bird_forum.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bird_forum.domain.dto.CategoryDTO;
import com.bird_forum.domain.po.Category;
import com.bird_forum.domain.query.CategoryQuery;
import com.bird_forum.mapper.CategoryMapper;
import com.bird_forum.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    /**
     * 添加分类
     * @param categoryDTO 分类参数
     * @return 是否成功
     */
    @Override
    public boolean addCategory(CategoryDTO categoryDTO) {
        return save(new Category(categoryDTO.getName()));
    }

    /**
     * 更新分类
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
     * @param categoryQuery 分页查询参数
     * @return 分类集合
     */
    @Override
    public List<Category> list(CategoryQuery categoryQuery) {
        return lambdaQuery()
                .like(BeanUtils.isNotNull(categoryQuery.getName()), Category::getName, categoryQuery.getName())
                .eq(BeanUtils.isNotNull(categoryQuery.getStatus()), Category::getStatus, categoryQuery.getStatus())
                .list(categoryQuery.toPage());
    }
}
