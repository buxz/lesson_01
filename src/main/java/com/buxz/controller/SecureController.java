package com.buxz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个控制器是需要我们获取授权Token后使用Token才可以访问到的，代码如下图17所示
 */
@RestController
@RequestMapping("/secure")
public class SecureController {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Secure Hello!";
    }

}
