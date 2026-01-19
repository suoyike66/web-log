package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.admin.model.vo.tag.AddTagReqVO;
import com.suoyike.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.suoyike.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.suoyike.weblog.common.utils.PageResponse;
import com.suoyike.weblog.common.utils.Response;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-11 15:36
 * @description: TODO
 **/
public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVO
     * @return
     */
    Response addTags(AddTagReqVO addTagReqVO);

    /**
     * 查询标签分页
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);
}

