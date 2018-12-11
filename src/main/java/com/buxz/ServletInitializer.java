package com.buxz;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
* SpringBootServletInitializer 是springboot提供的web程序初始化的入口
* 当我们使用外部容器（后期文章讲解使用外部tomcat如何运行项目）运行项目时会自动加载并且装配。
*
*
* */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Lesson01Application.class);
	}

}
