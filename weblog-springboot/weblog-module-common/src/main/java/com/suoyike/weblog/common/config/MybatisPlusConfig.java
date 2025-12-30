package com.suoyike.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-12-23 15:37
 * @description: Mybatis Plus 配置文件
 **/
@Configuration
@MapperScan("com.suoyike.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}
