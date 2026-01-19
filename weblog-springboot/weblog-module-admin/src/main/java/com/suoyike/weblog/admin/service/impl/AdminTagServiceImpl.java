package com.suoyike.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suoyike.weblog.admin.model.vo.tag.AddTagReqVO;
import com.suoyike.weblog.admin.service.AdminTagService;
import com.suoyike.weblog.common.domain.dos.TagDO;
import com.suoyike.weblog.common.domain.mapper.TagMapper;
import com.suoyike.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-01-11 15:35
 * @description: TODO
 **/
@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 添加标签集合
     *
     * @param addTagReqVO
     * @return
     */
    @Override
    public Response addTags(AddTagReqVO addTagReqVO) {
        List<String> tagNames = addTagReqVO.getTags();
        
        // 过滤掉空字符串和去重
        Set<String> uniqueTagNames = tagNames.stream()
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toSet());
        
        if (uniqueTagNames.isEmpty()) {
            return Response.success(); // 如果没有有效标签，直接返回
        }
        
        // 查询数据库中已存在的标签
        List<TagDO> existingTags = lambdaQuery()
                .in(TagDO::getName, uniqueTagNames)
                .list();
        
        Set<String> existingTagNames = existingTags.stream()
                .map(TagDO::getName)
                .collect(Collectors.toSet());
        
        // 只插入不存在的标签
        List<TagDO> tagsToInsert = uniqueTagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> TagDO.builder()
                        .name(name)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        
        if (!tagsToInsert.isEmpty()) {
            try {
                saveBatch(tagsToInsert);
            } catch (Exception e) {
                log.error("插入标签失败", e);
                return Response.fail("插入标签失败: " + e.getMessage());
            }
        }
        
        return Response.success();
    }
}
