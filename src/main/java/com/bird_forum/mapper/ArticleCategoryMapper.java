package com.bird_forum.mapper;

import com.bird_forum.domain.po.ArticleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bird_forum.domain.po.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    @Select("select c.id, c.name, c.icon, c.status from category c inner join article_category ac on c.id = ac.category_id where ac.article_id = #{articleId}")
    List<Category> listCategoriesByArticleId(Long articleId);
}
