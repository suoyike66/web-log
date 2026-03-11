package com.suoyike.weblog.admin.model.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-11 16:06
 * @description: 标签分页
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagPageListRspVO {

    /**
     * 标签 ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 文章总数
     */
    private Integer articlesTotal;
}
