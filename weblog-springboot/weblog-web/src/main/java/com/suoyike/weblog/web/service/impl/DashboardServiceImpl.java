package com.suoyike.weblog.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.suoyike.weblog.common.constant.Constants;
import com.suoyike.weblog.common.domain.dos.ArticlePublishCountDO;
import com.suoyike.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.suoyike.weblog.common.domain.mapper.ArticleMapper;
import com.suoyike.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import com.suoyike.weblog.common.utils.Response;
import com.google.common.collect.Lists;
import com.suoyike.weblog.common.constant.Constants;
import com.suoyike.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.suoyike.weblog.web.model.vo.dashboard.FindDashboardPVStatisticsInfoRspVO;
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

import static com.suoyike.weblog.common.constant.Constants.MONTH_DAY_FORMATTER;

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
    @Autowired
    private StatisticsArticlePVMapper statisticsArticlePVMapper;

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

    /**
     * 获取最近一周文章 PV 访问量统计信息
     *
     * @return
     */
    @Override
    public Response findArticlePVStatistics() {
        // 查询最近一周的 PV 访问量记录
        List<StatisticsArticlePVDO> articlePVDOS = statisticsArticlePVMapper.selectLatestWeekRecords();

        Map<LocalDate, Long> pvDateCountMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(articlePVDOS)) {
            // 转 Map, 方便后续通过日期获取 PV 访问量
            pvDateCountMap = articlePVDOS.stream()
                    .collect(Collectors.toMap(StatisticsArticlePVDO::getPvDate, StatisticsArticlePVDO::getPvCount));
        }

        // 日期集合
        List<String> pvDates = Lists.newArrayList();
        // PV 集合
        List<Long> pvCounts = Lists.newArrayList();

        // 当前日期
        LocalDate currDate = LocalDate.now();
        // 一周前（往前推6天，加上当天共7天）
        LocalDate tmpDate = currDate.minusDays(6);
        // 从7天前开始循环到今天
        for (; tmpDate.isBefore(currDate) || tmpDate.isEqual(currDate); tmpDate = tmpDate.plusDays(1)) {
            // 设置对应日期的 PV 访问量
            pvDates.add(tmpDate.format(MONTH_DAY_FORMATTER));
            Long pvCount = pvDateCountMap.get(tmpDate);
            pvCounts.add(Objects.isNull(pvCount) ? 0 : pvCount);
        }

        FindDashboardPVStatisticsInfoRspVO vo = FindDashboardPVStatisticsInfoRspVO.builder()
                .pvDates(pvDates)
                .pvCounts(pvCounts)
                .build();

        return Response.success(vo);
    }
}