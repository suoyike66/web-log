package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.suoyike.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.suoyike.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.suoyike.weblog.common.utils.PageResponse;
import com.suoyike.weblog.common.utils.Response;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-11 15:36
 * @description: TODO
 **/
public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
    
    /**
     * 分类分页数据查询
     * @param findCategoryPageListReqVO
     * @return
     */
    PageResponse findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO);

    /**
     * 删除分类
     * @param deleteCategoryReqVO
     * @return
     */
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);

    /**
     * 获取文章分类的 Select 列表数据
     * @return
     */
    Response findCategorySelectList();

}

