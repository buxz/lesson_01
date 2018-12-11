### 添加依赖

`

        <dependency>
			<!-- springboot 支持 JSP -->
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
		<dependency>
			<!-- srevlet 支持开启 -->
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
		</dependency>
		<dependency>
			<!-- jstl 支持开启 -->
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>
		
		
`

### 配置jsp目录
1. 在main 目录下新建webapp/WEB-INF/jsp文件夹 ，用来存储jsp文件
2. 修改 application.properties 

            spring.mvc.view.prefix=/WEB-INF/jsp/
            spring.mvc.view.suffix=.jsp
            
3. 创建index.jsp
4. 创建controller 
    
    
    @Controller
    public class IndexController {
        @RequestMapping(value = "/index",method = RequestMethod.GET)
        public String index(){
            return "index";
        }
    }