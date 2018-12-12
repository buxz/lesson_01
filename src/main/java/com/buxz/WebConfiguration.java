package com.buxz;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.buxz.interceptor.LoggerInterceptor;
import com.buxz.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * WebMvcConfigurerAdapter  SpringBoot内部提供专门处理用户自行添加的配置，里面不仅仅包含了修改视图的过滤还有其他很多的方法
 *
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter
{
    /**
     * 修改自定义消息转换器
     * @param converters 消息转换器列表
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //调用父类的配置
        super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤，配置多个过滤方式
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect, // 消除对同一对象循环引用的问题，默认为false
                SerializerFeature.WriteMapNullValue, // 是否输出值为null的字段,默认为false。
                SerializerFeature.WriteNullStringAsEmpty //字符类型字段如果为null,输出为"",而非null
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8); // 防止中文乱码
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // session拦截器
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        // 日志拦截器
        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
    }
}
