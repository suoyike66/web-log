package com.suoyike.weblog.web.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 15:03
 * @description: 首页
 **/
@RestController
@Api(tags = "首页")
public class IndexController {

    // @Autowired
    // private ArticleService articleService;
    //
    // @PostMapping("/article/list")
    // @ApiOperation(value = "获取首页文章分页数据")
    // @ApiOperationLog(description = "获取首页文章分页数据")
    // public Response findArticlePageList(@RequestBody FindIndexArticlePageListReqVO findIndexArticlePageListReqVO) {
    //     return articleService.findArticlePageList(findIndexArticlePageListReqVO);
    // }

}
