# 开启定时任务
> springboot 内部已经集成scheduled ，故开启定时任务只需要两步
## 1. 配置定时任务
1. 找到入口程序,添加注解@EnableScheduling
```java
package com.buxz;

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
		SpringApplication.run(Lesson01Application.class, args);
	}

}

```
2. 配置具体任务
```java
package com.buxz.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by SQ_BXZ on 2019-01-21.
 */
@Component
public class ScheduleTest {

    @Scheduled(cron = "10 * * * * *")
    public void cron(){
        System.out.println("执行测试时间：" + new Date(System.currentTimeMillis()));
    }

}

```

## Cron表达式 
1. 说明

关键字 | 可选值 | 有效范围
---|---|---
Seconds | ", - * /"四个字符 |　0-59的整数
Minutes | ", - * /"四个字符 |　0-59的整数
Hours |", - * /"四个字符　|　0-23的整数
DayofMonth|", - * / ? L W C"八个字符|　0-31的整数
Month|", - * /"四个字符|　1-12的整数或JAN-DEc
DayofWeek|", - * / ? L C #"四个字符|　1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推
Year |", - * /"四个字符|　1970-2099年

2. 简单示例

示例 | 说明
---|---
"0 0 12 * * ?"    | 每天中午十二点触发
"0 */1 * * * ?"   | 每隔1分钟执行一次
"*/5 * * * * ?"   | 每隔5秒执行一次
"0 15 10 ? * *" |   每天早上10：15触发
"0 15 10 * * ?" |   每天早上10：15触发
"0 15 10 * * ? *" |  每天早上10：15触发
"0 15 10 * * ? 2005" |   2005年的每天早上10：15触发
"0 * 14 * * ?" |   每天从下午2点开始到2点59分每分钟一次触发
"0 0/5 14 * * ?" |   每天从下午2点开始到2：55分结束每5分钟一次触发
"0 0/5 14,18 * * ?" |   每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
"0 0-5 14 * * ?"    | 每天14:00至14:05每分钟一次触发
"0 10,44 14 ? 3 WED"   | 三月的每周三的14：10和14：44触发
"0 15 10 ? * MON-FRI" |   每个周一、周二、周三、周四、周五的10：15触发