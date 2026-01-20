package com.suoyike.weblog.admin.convert;

import com.suoyike.weblog.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import com.suoyike.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.suoyike.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026/1/20 9:31
 * @description: 博客设置转换
 **/
@Mapper
public interface BlogSettingsConvert {
    /**
     * 初始化 convert 实例
     */
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 VO 转化为 DO
     * @param bean
     * @return
     */
    BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO bean);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    FindBlogSettingsRspVO convertDO2VO(BlogSettingsDO bean);

}