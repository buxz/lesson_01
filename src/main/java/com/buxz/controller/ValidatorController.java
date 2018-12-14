package com.buxz.controller;

import com.buxz.entity.DemoEntity;
import com.buxz.entity.UserEntity;
import com.buxz.jpa.UserJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * MessageSource 用于格式化错误信息
 *
 */
@RestController
public class ValidatorController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/validator")
    public String validator(@Valid DemoEntity entity, BindingResult result){
        if (result.hasErrors()){
            StringBuilder msg = new StringBuilder();
            // 获取错误字段集合
            List<FieldError> fieldErrors =result.getFieldErrors();
            // 获取本地locale, zh_CN
            Locale currentLocale = LocaleContextHolder.getLocale();
            // 遍历错误字段获取错误信息
            for (FieldError fieldError : fieldErrors) {
                // 获取错误信息
                String errorMessage = messageSource.getMessage(fieldError,currentLocale);
                // 添加到错误消息集合内
                msg.append(fieldError.getField()).append(" : ").append(errorMessage).append(" , ");
            }
            return msg.toString();
        }
        return "验证通过";
    }


}
