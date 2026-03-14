package com.suoyike.weblog.common.constant;

import com.suoyike.weblog.common.model.vo.wiki.FindWikiPageListRspVO;
import com.suoyike.weblog.common.domain.dos.WikiDO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WikiConvert {
    /**
     * 初始化 convert 实例
     */
    WikiConvert INSTANCE = Mappers.getMapper(WikiConvert.class);

    /**
     * WikiDO -> FindWikiPageListRspVO
     * @param bean
     * @return
     */
    @Mapping(target = "isTop", expression = "java(bean.getWeight() > 0)")
    FindWikiPageListRspVO convertDO2VO(WikiDO bean);

}
