package com.suoyike.weblog.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suoyike.weblog.common.enums.ResponseCodeEnum;
import com.suoyike.weblog.common.exception.BizException;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.suoyike.weblog.web.model.vo.comment.FindQQUserInfoRspVO;
import com.suoyike.weblog.web.service.CommentService;
import com.suoyike.weblog.web.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2023-09-15 14:03
 * @description: 知识库
 **/
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api-key}")
    private String apiKey;

    @Override
    public Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO) {
        String qq = findQQUserInfoReqVO.getQq();

        // 校验 QQ 号
        if (!StringUtil.isPureNumber(qq)) {
            log.warn("昵称输入的格式不是 QQ 号: {}", qq);
            throw new BizException(ResponseCodeEnum.NOT_QQ_NUMBER);
        }

        // 请求第三方接口
        String url = String.format("http://api.guiguiya.com/api/qq_info?qq=%s&apiKey=%s", qq, apiKey);
        String result = restTemplate.getForObject(url, String.class);

        log.info("通过 QQ 号获取用户信息: {}", result);

        // 解析响参
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(result, Map.class);
            if (Objects.equals(map.get("code"), HttpStatus.OK.value())) {

                // 获取响应参数中 data 节点下的数据
                Map<String, Object> data = (Map<String, Object>) map.get("data");

                if (!CollectionUtils.isEmpty(data)) {
                    // 获取用户头像、昵称、邮箱
                    return Response.success(FindQQUserInfoRspVO.builder()
                            .avatar(String.valueOf(data.get("avatar_apiurl_1")))
                            .nickname(String.valueOf(data.get("name")))
                            .mail(qq.trim() + "@qq.com") // 组合邮箱地址
                            .build());
                }
            }

            return Response.fail();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}