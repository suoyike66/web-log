package {{packageName}}.service;

import {{packageName}}.model.vo.{{entityNameLowerFirst}}.*;
import {{commonPackage}}.utils.Response;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}服务
 **/
public interface {{serviceName}} {

    Response create{{entityName}}(Create{{entityName}}ReqVO create{{entityName}}ReqVO);

    Response update{{entityName}}(Update{{entityName}}ReqVO update{{entityName}}ReqVO);

    Response delete{{entityName}}(Delete{{entityName}}ReqVO delete{{entityName}}ReqVO);

    Response find{{entityName}}PageList(Find{{entityName}}PageListReqVO find{{entityName}}PageListReqVO);
    
    Response find{{entityName}}Detail(Find{{entityName}}DetailReqVO find{{entityName}}DetailReqVO);
}