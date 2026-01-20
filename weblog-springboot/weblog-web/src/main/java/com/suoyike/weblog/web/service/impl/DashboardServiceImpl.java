package com.suoyike.weblog.web.service.impl;

import com.google.common.collect.Maps;
import com.suoyike.weblog.common.domain.dos.ArticlePublishCountDO;
import com.suoyike.weblog.common.domain.mapper.ArticleMapper;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-21 10:26
 * @description: 仪表盘服务实现
 **/
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取文章发布热点统计信息
     *
     * @return
     */
    @Override
    public Response findArticlePublishHotStatistics() {
        // 当前日期
        LocalDate currDate = LocalDate.now();

        // 当前日期倒退一年的日期
        LocalDate startDate = currDate.minusYears(1);

        // 查找这一年内，每日发布的文章数量
        List<ArticlePublishCountDO> articlePublishCountDOS = articleMapper.selectDateArticlePublishCount(startDate, currDate.plusDays(1));

        // 如果没有查询到数据，返回空的有序Map
        if (CollectionUtils.isEmpty(articlePublishCountDOS)) {
            Map<LocalDate, Long> map = Maps.newLinkedHashMap();
            // 从上一年的今天循环到今天
            LocalDate loopDate = currDate.minusYears(1);
            for (; loopDate.isBefore(currDate) || loopDate.isEqual(currDate); loopDate = loopDate.plusDays(1)) {
                map.put(loopDate, 0L);
            }
            return Response.success(map);
        }

        // DO 转 Map
        Map<LocalDate, Long> dateArticleCountMap = articlePublishCountDOS.stream()
                .collect(Collectors.toMap(ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));

        // 有序 Map, 返回的日期文章数需要以升序排列
        Map<LocalDate, Long> map = Maps.newLinkedHashMap();
        // 从上一年的今天循环到今天
        LocalDate loopDate = currDate.minusYears(1);
        for (; loopDate.isBefore(currDate) || loopDate.isEqual(currDate); loopDate = loopDate.plusDays(1)) {
            // 以日期作为 key 从 dateArticleCountMap 中取文章发布总量
            Long count = dateArticleCountMap.get(loopDate);
            // 设置到返参 Map
            map.put(loopDate, Objects.isNull(count) ? 0 : count);
        }

        return Response.success(map);
    }
}