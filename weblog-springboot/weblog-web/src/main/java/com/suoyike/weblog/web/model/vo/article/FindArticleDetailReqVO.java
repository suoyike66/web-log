package com.suoyike.weblog.web.model.vo.article;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 21:50
 * @description: 文章详情
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询文章详情 VO")
public class FindArticleDetailReqVO {
    /**
     * 文章 ID
     */
    @NotNull(message = "文章 ID 不能为空")
    private Long id;
}
