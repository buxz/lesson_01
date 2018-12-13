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
    
### 配置静态文件路径
1. WebConfiguration 重写 addResourceHandlers() 添加静态路径

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
    
### 配置servlet 

1. 新建servlet 的 类， 继承 HttpServlet
    
    
    @WebServlet(urlPatterns = "/test2")
    public class TestServlet extends HttpServlet
    {
        //重写get方法
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //设置返回类型为json
            response.setContentType("application/json");
            //设置返回字符集
            response.setCharacterEncoding("utf-8");
            //输出对象
            PrintWriter writer = response.getWriter();
            //输出error消息
            writer.write("执行TestServlet内doGet方法成功!");
            writer.close();
        }
    }
2. 在项目启动文件上添加@ServletComponentScan 注释，扫描并注册系统内所有被添加了 @WebServlet 的servlet



        
        
        
        

        
        
        

    
    
    
    
    
    
    

