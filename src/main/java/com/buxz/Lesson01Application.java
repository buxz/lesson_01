package com.buxz;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
* @ServletComponentScan 扫描系统下的 @WebServlet 注册自定义的servlet
*
* @SpringBootApplication =
*   @Configuration + (经常与@Bean 组合使用，使用这两个注解就可以创建一个简单的Spring 配置类， 可以用来替代相应的XML 配置文件)
*   @EnableAutoConfiguration + (能够自动配置Spring 的上下文，猜测和配置用户想要的Bean类。)
*   @ComponentScan (会自动扫描指定包下的全部标有@Component 的类， 并注册成Bean，包括子注解@Service 、@Repository 、@Controller。这些Bean 一般是结合@Autowired 构造函数来注入。)
*   @EnableScheduling  开启定时任务配置springboot内部会对应原始配置定时任务添加对应的配置文件
*
*
* */
@ServletComponentScan
@SpringBootApplication
@EnableScheduling
public class Lesson01Application {

	/*
	*
	* main 方法通过调用Run，将业务委托给了Spring Boot 的SpringApplication 类。
	* SpringApplication将引导用户的应用启动并相应地启动被自动配置的Tomcat Web 服务器
	*
	* */
	public static void main(String[] args) {
	    //
//		SpringApplication springApplication = new SpringApplication(Lesson01Application.class);
//		springApplication.setBannerMode(Banner.Mode.OFF);
//		springApplication.run(args);

		SpringApplication.run(Lesson01Application.class, args);
	}

}
