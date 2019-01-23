package com.buxz.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.buxz.interceptor.JwtTokenInterceptor;
import com.buxz.interceptor.LoggerInterceptor;
import com.buxz.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

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
        registry.addViewController("/websocket").setViewName("websocket");
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
     * 拦截器起作用顺序 按照代码顺序执行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // session拦截器
//        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        // 日志拦截器
        registry.addInterceptor(new JwtTokenInterceptor()).addPathPatterns("/api/**");
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

    /**
     * 跨域请求配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/*") // 配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
                .allowedMethods("*") // 允许所有的请求方法访问该跨域资源服务器，如：POST、GET、
                .allowedOrigins("*") // 允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
                .allowedHeaders("*"); // 允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"
    }

}
