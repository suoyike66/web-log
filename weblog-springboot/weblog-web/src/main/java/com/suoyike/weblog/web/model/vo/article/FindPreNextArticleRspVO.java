package com.suoyike.weblog.web.model.vo.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 21:52
 * @description: 文章上下页
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPreNextArticleRspVO {
    /**
     * 文章 ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;
}
