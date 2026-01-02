package com.bird_forum.service;

import com.bird_forum.domain.dto.CategoryDTO;
import com.bird_forum.domain.po.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bird_forum.domain.query.CategoryQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 添加分类
     * @param categoryDTO 分类参数
     * @return 是否添加成功
     */
    boolean addCategory(CategoryDTO categoryDTO);

    /**
     * 更新分类
     * @param categoryDTO 分类参数
     * @return 是否更新成功
     */
    boolean update(CategoryDTO categoryDTO);

    /**
     * 获取分类集合
     * @param categoryQuery 查询参数
     * @return 分类集合
     */
    List<Category> list(CategoryQuery categoryQuery);
}
