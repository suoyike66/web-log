package {{packageName}}.controller;

import {{packageName}}.model.vo.{{entityNameLowerFirst}}.*;
import {{packageName}}.service.{{entityName}}Service;
import {{commonPackage}}.aspect.ApiOperationLog;
import {{commonPackage}}.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}模块
 **/
@RestController
@RequestMapping("/{{module}}/{{entityNameLowerFirst}}")
@Api(tags = "{{moduleDesc}} {{entityName}}模块")
public class {{className}} {

    @Autowired
    private {{entityName}}Service {{entityNameLowerFirst}}Service;

    @PostMapping("/create")
    @ApiOperation(value = "创建{{entityName}}")
    @ApiOperationLog(description = "创建{{entityName}}")
    @PreAuthorize("hasRole('{{securityRole}}')")
    public Response create{{entityName}}(@RequestBody @Validated Create{{entityName}}ReqVO create{{entityName}}ReqVO) {
        return {{entityNameLowerFirst}}Service.create{{entityName}}(create{{entityName}}ReqVO);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新{{entityName}}")
    @ApiOperationLog(description = "更新{{entityName}}")
    @PreAuthorize("hasRole('{{securityRole}}')")
    public Response update{{entityName}}(@RequestBody @Validated Update{{entityName}}ReqVO update{{entityName}}ReqVO) {
        return {{entityNameLowerFirst}}Service.update{{entityName}}(update{{entityName}}ReqVO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除{{entityName}}")
    @ApiOperationLog(description = "删除{{entityName}}")
    @PreAuthorize("hasRole('{{securityRole}}')")
    public Response delete{{entityName}}(@RequestBody @Validated Delete{{entityName}}ReqVO delete{{entityName}}ReqVO) {
        return {{entityNameLowerFirst}}Service.delete{{entityName}}(delete{{entityName}}ReqVO);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询{{entityName}}分页数据")
    @ApiOperationLog(description = "查询{{entityName}}分页数据")
    public Response find{{entityName}}PageList(@RequestBody @Validated Find{{entityName}}PageListReqVO find{{entityName}}PageListReqVO) {
        return {{entityNameLowerFirst}}Service.find{{entityName}}PageList(find{{entityName}}PageListReqVO);
    }
    
    @PostMapping("/detail")
    @ApiOperation(value = "查询{{entityName}}详情")
    @ApiOperationLog(description = "查询{{entityName}}详情")
    public Response find{{entityName}}Detail(@RequestBody @Validated Find{{entityName}}DetailReqVO find{{entityName}}DetailReqVO) {
        return {{entityNameLowerFirst}}Service.find{{entityName}}Detail(find{{entityName}}DetailReqVO);
    }
}