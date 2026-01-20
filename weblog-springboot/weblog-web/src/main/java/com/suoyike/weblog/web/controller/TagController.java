package com.suoyike.weblog.web.controller;

import com.suoyike.weblog.common.aspect.ApiOperationLog;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-01-20 15:33
 * @description: 标签
 **/
@RestController
@RequestMapping("/tag")
@Api(tags = "标签")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/list")
    @ApiOperation(value = "前台获取标签列表")
    @ApiOperationLog(description = "前台获取标签列表")
    public Response findTagList() {
        return tagService.findTagList();
    }

}
