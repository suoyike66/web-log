package com.suoyike.weblog.admin.convert;

import com.suoyike.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.suoyike.weblog.common.domain.dos.CommentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026/03/16 14:57
 * @description: 评论实体类转换
 **/
@Mapper
public interface CommentConvert {
    /**
     * 初始化 convert 实例
     */
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    FindCommentPageListRspVO convertDO2VO(CommentDO bean);

}
