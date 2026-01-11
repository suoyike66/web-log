package com.suoyike.weblog.admin.service.impl;

import com.suoyike.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.suoyike.weblog.admin.service.AdminCategoryService;
import com.suoyike.weblog.common.domain.dos.CategoryDO;
import com.suoyike.weblog.common.domain.mapper.CategoryMapper;
import com.suoyike.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-01-11 15:35
 * @description: TODO
 **/
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     *
     * @param addCategoryReqVO
     * @return
     */
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        // 构建 DO 类
        CategoryDO categoryDO = CategoryDO.builder()
                .name(addCategoryReqVO.getName())
                .build();

        // 执行 insert
        categoryMapper.insert(categoryDO);

        return Response.success();
    }
}
