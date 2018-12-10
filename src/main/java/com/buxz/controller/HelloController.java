package com.buxz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SQ_BXZ on 2018-12-10.
 * @RestController =
 *  @Controller   +
 *  @ResponseBody (该注解用于将Controller 的方法返回对象， 通过适当的HttpMessageConverter转换为指定格式后，写入Response对象的body数据区。)
 *
 *
 */
@RestController
public class HelloController {

    @RequestMapping("/helloworld")
    public String HelloWorld(){
        return "Hello World!";
    }
}
