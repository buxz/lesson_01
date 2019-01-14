package com.buxz.exceptions;

import org.springframework.web.bind.annotation.*;

/**
 * 统一异常处理
 * 具体Controller不需要进行单独异常处理，由该类统一处理
 *
 *
 * 全局异常信息配置
 * @ControllerAdvice 用来配置控制器通知的
 * @ResponseBody  全局返回的都是Json格式的字符串
 *
 */
@ControllerAdvice(annotations = RestController.class) //也就是只有添加了@RestController注解的控制器才会进入全局异常处理
@ResponseBody
public class RestExceptionHandler {

    /**
     *
     * 默认统一异常处理方法
     *  @ExceptionHandler 注解用来配置需要拦截的异常类型，默认是全局类型
     *  @ResponseStatus 注解用于配置遇到该异常后返回数据时的StatusCode的值，我们这里默认使用值500
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus
    public ApiResult runtimeExceptionHandler(Exception e){
        e.printStackTrace();

        return ApiResultGenerator.errorResult(e.getMessage(),e);
    }


}
