package com.bird_forum.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询参数
 *
 * @author xiaoymin
 * 2020/05/07 14:05
 */
@Schema(description = "分页查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class PageQuery {
    /**
     * 当前页码
     */
    @Schema(description = "当前页码")
    private Integer pageNo;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量")
    private Integer pageSize;

//    private String sortBy;
//
//    private Boolean isAsc;

    public <V> Page<V> toPage() {
        return new Page<V>()
                .setCurrent(pageNo)
                .setSize(pageSize);
//                .addOrder(new OrderItem()
//                        .setColumn(sortBy.isBlank() ? "update_time" : sortBy)
//                        .setAsc(isAsc));
    }
}
