package com.suoyike.weblog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-12-22 11:44
 * @description: 业务异常
 **/
@Getter
@Setter
public class BizException extends RuntimeException {
    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
