package {{packageName}}.convert;

import {{commonPackage}}.domain.dos.{{entityName}}DO;
import {{packageName}}.model.vo.{{entityNameLowerFirst}}.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}转换器
 **/
@Mapper
public interface {{converterName}} {
    /**
     * 初始化 convert 实例
     */
    {{converterName}} INSTANCE = Mappers.getMapper({{converterName}}.class);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    Find{{entityName}}DetailRspVO convertDO2VO({{entityName}}DO bean);

    /**
     * 将 DO 转化为分页 VO
     * @param bean
     * @return
     */
    Find{{entityName}}PageListRspVO convertDO2PageVO({{entityName}}DO bean);
}