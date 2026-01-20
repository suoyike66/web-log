package com.suoyike.weblog.web.convert;

import com.suoyike.weblog.common.domain.dos.BlogSettingsDO;
import com.suoyike.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 15:45
 * @description: 博客设置转换
 **/
@Mapper
public interface BlogSettingsConvert {
    /**
     * 初始化 convert 实例
     */
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    FindBlogSettingsDetailRspVO convertDO2VO(BlogSettingsDO bean);

}
