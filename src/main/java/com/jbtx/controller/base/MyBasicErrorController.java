package com.jbtx.controller.base;

import com.jbtx.common.ResponseBean;
import com.jbtx.enums.UnicomResponseEnums;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyou
 * @title: MyBasicErrorController
 * @projectName financial
 * @description: 异常拦截
 * @date 2019/6/611:31
 */
@RestController
public class MyBasicErrorController implements ErrorController {

    private static final String ERROR_PATH="/error";
    @RequestMapping("/error")
    public ResponseBean<Object> handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode==404){
            return new ResponseBean<>(false,null, UnicomResponseEnums.CODE_404);
        }else {
            return new ResponseBean<>(false,null,UnicomResponseEnums.CODE_003);
        }

    }
    @RequestMapping("/toLogin")
    public ResponseBean<Object> handleLogin(){
        return new ResponseBean<>(false,null, UnicomResponseEnums.CODE_3000);

    }
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
