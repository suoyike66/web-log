package com.suoyike.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suoyike.weblog.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-01-11 15:28
 * @description: TODO 标签 Mapper
 **/
public interface TagMapper extends BaseMapper<TagDO> {
    /**
     * 根据条件查询标签列表
     *
     * @param current 当前页
     * @param size  每页大小
     * @param name  标签名
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 标签列表
     */
    default Page<TagDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate) {
        // 分页对象
        Page<TagDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(Objects.nonNull(name), TagDO::getName, name) // 模糊查询
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate) // 大于等于开始时间
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate) // 小于等于结束时间
                .orderByDesc(TagDO::getCreateTime); // order by create_time desc

        return selectPage(page, wrapper);
    }
}

