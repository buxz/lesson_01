# banner修改
## 1. 不显示banner
```java

@ServletComponentScan
@SpringBootApplication
@EnableScheduling
public class Lesson01Application {

	public static void main(String[] args) {
	    //
		SpringApplication springApplication = new SpringApplication(Lesson01Application.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);

//		SpringApplication.run(Lesson01Application.class, args);
	}

}

```
## 2. 修改banner
1. 原启动文件不变
2. 在 resouces 路径下 添加 banner.txt 既可覆盖原banner信息
## 3. 常用的图片转字符画的网站

> 图片转字符画   
https://www.degraeve.com/img2txt.php 

