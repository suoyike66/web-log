package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-21 10:25
 * @description: 仪表盘服务
 **/
public interface DashboardService {

    /**
     * 获取文章发布热点统计信息
     * @return
     */
    Response findArticlePublishHotStatistics();

    /**
     * 获取最近一周文章 PV 访问量统计信息
     * @return
     */
    Response findArticlePVStatistics();
}