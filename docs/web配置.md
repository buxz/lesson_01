### 配置fastJson
1. 添加依赖 
        
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>RELEASE</version>
        </dependency>
        
2. 配置 WebConfiguration 类

    FastJson SerializerFeatures
    
    WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
    
    WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
    
    DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
    
    WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
    
    WriteMapNullValue：是否输出值为null的字段,默认为false。（数据库的字段值如果是后来删除的在数据库中显示的为空字符串，只有在初始化或者通过sql语句才可以置空）
     
        
### 配置拦截器

1. 创建 SessionInterceptor 实体类 实现 HandlerInterceptor接口 
2. 配置 SessionInterceptor 到 SpringBoot 中 
    1. WebConfiguration 中 通过重写 addInterceptors 方法 此处配置拦截器

        
        
        
        

        
        
        

    
    
    
    
    
    
    

