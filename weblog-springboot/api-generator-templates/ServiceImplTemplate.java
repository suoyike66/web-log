package {{packageName}}.service.impl;

import {{commonPackage}}.domain.dos.{{entityName}}DO;
import {{commonPackage}}.domain.mapper.{{entityName}}Mapper;
import {{commonPackage}}.enums.ResponseCodeEnum;
import {{commonPackage}}.exception.BizException;
import {{commonPackage}}.utils.PageResponse;
import {{packageName}}.convert.{{entityName}}Convert;
import {{packageName}}.model.vo.{{entityNameLowerFirst}}.*;
import {{packageName}}.service.{{entityName}}Service;
import {{commonPackage}}.utils.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}服务实现
 **/
@Service
@Slf4j
public class {{serviceNameImpl}} implements {{entityName}}Service {

    @Autowired
    private {{entityName}}Mapper {{entityNameLowerFirst}}Mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response create{{entityName}}(Create{{entityName}}ReqVO create{{entityName}}ReqVO) {
        // VO 转 DO
        {{entityName}}DO {{entityNameLowerFirst}}DO = {{entityName}}DO.builder()
                {{#each fields}}
                .{{this.name}}(create{{../entityName}}ReqVO.get{{capitalize this.name}}())
                {{/each}}
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        {{entityNameLowerFirst}}Mapper.insert({{entityNameLowerFirst}}DO);
        return Response.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response update{{entityName}}(Update{{entityName}}ReqVO update{{entityName}}ReqVO) {
        // 检查记录是否存在
        {{entityName}}DO existing{{entityName}} = {{entityNameLowerFirst}}Mapper.selectById(update{{entityName}}ReqVO.getId());
        if (Objects.isNull(existing{{entityName}})) {
            log.warn("==> {{entityName}}不存在, id: {}", update{{entityName}}ReqVO.getId());
            throw new BizException(ResponseCodeEnum.{{entityNameUpperCase}}_NOT_FOUND);
        }

        // VO 转 DO
        {{entityName}}DO {{entityNameLowerFirst}}DO = {{entityName}}DO.builder()
                .id(update{{entityName}}ReqVO.getId())
                {{#each fields}}
                .{{this.name}}(update{{../entityName}}ReqVO.get{{capitalize this.name}}())
                {{/each}}
                .updateTime(LocalDateTime.now())
                .build();

        int count = {{entityNameLowerFirst}}Mapper.updateById({{entityNameLowerFirst}}DO);
        if (count == 0) {
            log.warn("==> 更新{{entityName}}失败, id: {}", update{{entityName}}ReqVO.getId());
            throw new BizException(ResponseCodeEnum.{{entityNameUpperCase}}_UPDATE_FAILED);
        }
        return Response.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response delete{{entityName}}(Delete{{entityName}}ReqVO delete{{entityName}}ReqVO) {
        int count = {{entityNameLowerFirst}}Mapper.deleteById(delete{{entityName}}ReqVO.getId());
        if (count == 0) {
            log.warn("==> 删除{{entityName}}失败, id: {}", delete{{entityName}}ReqVO.getId());
            throw new BizException(ResponseCodeEnum.{{entityNameUpperCase}}_DELETE_FAILED);
        }
        return Response.success();
    }

    @Override
    public Response find{{entityName}}PageList(Find{{entityName}}PageListReqVO find{{entityName}}PageListReqVO) {
        // 分页对象
        Page<{{entityName}}DO> page = new Page<>(find{{entityName}}PageListReqVO.getCurrent(), find{{entityName}}PageListReqVO.getSize());

        // 构建查询条件
        LambdaQueryWrapper<{{entityName}}DO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                {{#each queryFields}}
                .like(StringUtils.isNotBlank(find{{../entityName}}PageListReqVO.get{{capitalize this.name}}()), {{entityName}}DO::get{{capitalize this.name}}, find{{../entityName}}PageListReqVO.get{{capitalize this.name}}())
                {{/each}}
                .orderByDesc({{entityName}}DO::getCreateTime); // 按创建时间倒序

        Page<{{entityName}}DO> result = {{entityNameLowerFirst}}Mapper.selectPage(page, wrapper);

        // DO 转 VO
        PageResponse<Find{{entityName}}PageListRspVO> pageResponse = PageResponse.<Find{{entityName}}PageListRspVO>builder()
                .current(result.getCurrent())
                .size(result.getSize())
                .total(result.getTotal())
                .records(result.getRecords().stream()
                        .map(doObj -> {{entityName}}Convert.INSTANCE.convertDO2VO(doObj))
                        .collect(Collectors.toList()))
                .build();

        return Response.success(pageResponse);
    }
    
    @Override
    public Response find{{entityName}}Detail(Find{{entityName}}DetailReqVO find{{entityName}}DetailReqVO) {
        {{entityName}}DO {{entityNameLowerFirst}}DO = {{entityNameLowerFirst}}Mapper.selectById(find{{entityName}}DetailReqVO.getId());
        if (Objects.isNull({{entityNameLowerFirst}}DO)) {
            log.warn("==> {{entityName}}不存在, id: {}", find{{entityName}}DetailReqVO.getId());
            throw new BizException(ResponseCodeEnum.{{entityNameUpperCase}}_NOT_FOUND);
        }

        // DO 转 VO
        Find{{entityName}}DetailRspVO vo = {{entityName}}Convert.INSTANCE.convertDO2VO({{entityNameLowerFirst}}DO);
        return Response.success(vo);
    }
}