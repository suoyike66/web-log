package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.suoyike.weblog.common.utils.Response;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 9:03
 * @description: TODO
 **/
public interface AdminBlogSettingsService {
    /**
     * 更新博客设置信息
     * @param updateBlogSettingsReqVO
     * @return
     */
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
}