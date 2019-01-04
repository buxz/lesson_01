package com.buxz.controller;

import com.alibaba.fastjson.JSONObject;
import com.buxz.utils.LoggerUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api")
public class APIController {

    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public String login(HttpServletRequest request, String name) throws Exception
    {
        return "success";
    }
}
