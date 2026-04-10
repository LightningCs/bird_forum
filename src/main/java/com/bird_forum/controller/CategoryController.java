package com.bird_forum.controller;


import com.bird_forum.domain.ResponseData;
import com.bird_forum.domain.dto.CategoryDTO;
import com.bird_forum.domain.po.Category;
import com.bird_forum.domain.query.CategoryQuery;
import com.bird_forum.domain.vo.CategoryVO;
import com.bird_forum.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author csh
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/category")
@Tag(name = "分类模块")
@Slf4j
public class CategoryController {
    @Resource
    private ICategoryService iCategoryService;

    /**
     * 新增分类
     *
     * @param categoryDTO 分类信息
     * @return 响应数据
     */
    @PostMapping
    @Operation(summary = "新增分类", description = "新增分类", method = "POST")
    public ResponseData addCategory(@ModelAttribute CategoryDTO categoryDTO) {
        log.info("新增分类: {}", categoryDTO);

        if (iCategoryService.addCategory(categoryDTO)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 删除分类
     *
     * @param id 分类id
     * @return 响应数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类", description = "删除分类", method = "DELETE")
    public ResponseData deleteCategory(@PathVariable Long id) {
        log.info("删除分类: {}", id);

        if (iCategoryService.removeById(id)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 更新分类
     *
     * @param categoryDTO 分类信息
     * @return 响应数据
     */
    @PutMapping
    @Operation(summary = "更新分类", description = "更新分类", method = "PUT")
    public ResponseData updateCategory(@ModelAttribute CategoryDTO categoryDTO) {
        log.info("更新分类: {}", categoryDTO);

        if (iCategoryService.update(categoryDTO)) {
            return ResponseData.success();
        }

        return ResponseData.error();
    }

    /**
     * 获取分类
     *
     * @param id 分类id
     * @return 响应数据
     */
    @GetMapping
    @Operation(summary = "获取分类", description = "获取分类", method = "GET")
    public ResponseData<Category> getCategory(Long id) {
        log.info("获取分类: {}", id);

        return ResponseData.success(iCategoryService.getById(id));
    }

    /**
     * 获取分类列表
     *
     * @param categoryQuery 分类查询参数
     * @return 响应数据
     */
    @GetMapping("/list")
    @Operation(summary = "获取分类列表", description = "获取分类列表", method = "GET")
    public ResponseData<List<CategoryVO>> listCategory(CategoryQuery categoryQuery) {
        List<CategoryVO> resList = iCategoryService.list(categoryQuery);
        return ResponseData.success(resList.subList((categoryQuery.getPageNo() - 1) * categoryQuery.getPageSize(), Math.min(categoryQuery.getPageNo() * categoryQuery.getPageSize(), resList.size())));
    }
}
