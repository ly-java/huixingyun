package com.jbtx.exception;

import com.jbtx.enums.UnicomResponseEnums;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaoyou
 * @title: UnicomRuntimeException
 * @projectName financial
 * @description: 自定义异常
 * @date 2019/6/517:07
 */
@Setter
@Getter
public class UnicomRuntimeException extends  RuntimeException {

    private static final long serialVersionUID = 1L;
    protected String code;

    protected String msg;

    protected String message;//打印出的日志信息

    public UnicomRuntimeException(UnicomResponseEnums enums, String message) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.message = message;
    }

    public UnicomRuntimeException(UnicomResponseEnums enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public UnicomRuntimeException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public UnicomRuntimeException() {
        super();
    }

    public UnicomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnicomRuntimeException(String message) {
        super(message);
        this.msg = message;
    }
}
