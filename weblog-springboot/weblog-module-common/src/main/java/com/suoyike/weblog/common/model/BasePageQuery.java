package com.suoyike.weblog.common.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-11 15:49
 * @description: TODO
 **/
@Data
public class BasePageQuery {
    /**
     * 当前页码, 默认第一页
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为1")
    @Max(value = 9999, message = "页码最大值为9999")
    private Long current = 1L;
    
    /**
     * 每页展示的数据数量，默认每页展示 10 条数据
     */
    @NotNull(message = "每页显示数量不能为空")
    @Min(value = 1, message = "每页最少显示1条数据")
    @Max(value = 100, message = "每页最多显示100条数据")
    private Long size = 10L;
}