package com.jbtx.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @file: ValidationException
 * @Description: 验证异常
 * @Author: ls
 * @Date: 2019/10/26 12:01
 */
@Getter
@Setter
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected String msg;

    public ValidationException(String msg){
        this.msg = msg;
    }

}
