package com.suoyike.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: 蓑衣客
 * @url: www.suoyikehan.com
 * @date: 2026-03-11 13:29
 * @description: 文章发布事件
 **/

@Getter
public class PublishArticleEvent extends ApplicationEvent {

    /**
     * 文章 ID
     */
    private Long articleId;

    public PublishArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}