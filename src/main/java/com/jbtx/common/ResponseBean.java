package com.jbtx.common;

import com.jbtx.enums.UnicomResponseEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaoyou
 * @title: ResponseBean
 * @projectName financial
 * @description: 返回json数据结构
 * @date 2019/6/516:58
 */
@Data
@ApiModel(value = "接口返回模型")
public class ResponseBean<T> {

    @ApiModelProperty(value = "成功/失败")
    private boolean success;

    @ApiModelProperty(value = "结果集")
    private T data;
    @ApiModelProperty(value = "状态码")
    private String errCode;

    @ApiModelProperty(value = "错误信息")
    private String errMsg;

    public ResponseBean(){}

    public ResponseBean(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "success=" + success +
                ", data=" + data +
                ", errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }

    public ResponseBean(boolean success, T data, String errCode, String errMsg) {
        super();
        this.success = success;
        this.data = data;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseBean(boolean success, String errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    public ResponseBean(boolean success, UnicomResponseEnums enums){
        this.success=success;
        this.errCode=enums.getCode();
        this.errMsg=enums.getMsg();
    }
    public ResponseBean(boolean success, T data, UnicomResponseEnums enums){
        this.success=success;
        this.data=data;
        this.errCode=enums.getCode();
        this.errMsg=enums.getMsg();
    }
}
