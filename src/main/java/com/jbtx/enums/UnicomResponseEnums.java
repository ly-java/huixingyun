package com.jbtx.enums;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaoyou
 * @title: UnicomResponseEnums
 * @projectName financial
 * @description: 枚举提示信息
 * @date 2019/6/517:00
 */
public enum UnicomResponseEnums {
    CODE_001("001", "数据库异常"),
    CODE_002("002", "网络异常"),
    CODE_003("003", "不合法的请求方式"),
    CODE_004("004", "找不到方法！"),

    CODE_200("200", "访问成功"),
    CODE_401("401", "非法请求，参数错误"),
    CODE_404("404", "找不到请求路径！"),
    CODE_500("500", "系统内部错误"),
    CODE_3000("3000", "请登录"),
    CODE_3001("30001", "密码错误次数过多");


    private String code;

    @ApiModelProperty(value = "状态消息", dataType = "String", required = true)
    private String msg;

    private UnicomResponseEnums(String code, String msg) {

        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
