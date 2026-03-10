package com.suoyike.weblog.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author: 蓑衣客
 * @url: www.suoyikehan.com
 * @date: 2026/03/10 22:42
 * @description: 全局静态变量
 **/
public interface Constants {
    /**
     * 月-日 格式
     */
    DateTimeFormatter MONTH_DAY_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");

    /**
     * 年-月-日 小时-分钟-秒
     */
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
