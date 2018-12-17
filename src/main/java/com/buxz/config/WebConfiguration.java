package com.buxz.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.buxz.interceptor.LoggerInterceptor;
import com.buxz.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
     * 添加路径访问
     * 可以通过Get形式的/login访问到我们的login.jsp
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
    }

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
//        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        // 日志拦截器
        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
    }

    /**
     * 自定义静态资源文件路径
     *
     * 我们配置了静态资源的路径为/resources/**，
     * 那么只要访问地址前缀是/resources/，
     * 就会被自动转到项目根目录下的static文件夹内。
     * 如：我们访问：127.0.0.1:8080/resources/t.png就会被解析成127.0.0.1:8080/t.png。
     * @param registry
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
    }

}
