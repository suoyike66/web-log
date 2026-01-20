package com.suoyike.weblog.web.controller;

import com.suoyike.weblog.common.aspect.ApiOperationLog;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-21 10:30
 * @description: 仪表盘
 **/
@RestController
@RequestMapping("/dashboard")
@Api(tags = "仪表盘")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/article/publish/hot")
    @ApiOperation(value = "获取文章发布热点统计信息")
    @ApiOperationLog(description = "获取文章发布热点统计信息")
    public Response findArticlePublishHotStatistics() {
        return dashboardService.findArticlePublishHotStatistics();
    }

    @PostMapping("/article/pv/statistics")
    @ApiOperation(value = "获取最近一周文章 PV 访问量统计信息")
    @ApiOperationLog(description = "获取最近一周文章 PV 访问量统计信息")
    public Response findArticlePVStatistics() {
        return dashboardService.findArticlePVStatistics();
    }
}