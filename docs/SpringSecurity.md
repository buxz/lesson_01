# 整合 SpringSecurity
- 认证，认证用户
- 授权，授权用户资源
- 默认用户名   user
- 默认登录密码 在项目启动的时候 
    Using default security password: 6c920ced-f1c1-4604-96f7-f0ce4e46f5d4

## 1. 添加依赖
```xml
        <!-- SpringSecurity 安全框架-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- SpringSecurity为我们提供的JSTL标签库 -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-taglibs</artifactId>
        </dependency>
```

## 2. 配置SpringSecurity
```java

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 完成自定义认证实体注入
     * 重写 userDetailsService() 方法
     * @return
     */
    @Override
    UserDetailsService userDetailsService(){
        return new UserService();
    }

    /**
     * 在内存中创建了一个用户，该用户的名称为bxz，密码为 021213 ，用户角色为USER。
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("bxz")
//                .password("021213")
//                .roles("USER");
//    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() // authorizeRequests()  定义哪些URL需要被保护、哪些不需要被保护
                    .antMatchers("/", "/home").permitAll() // "/" 和 "home" 不需要任何认证就可以访问
                    .anyRequest().authenticated() // 所有请求必须登录之后访问
                .and().formLogin() // formLogin() 定义当需要用户登录时候，转到的登录页面
                    .loginPage("/login") // http://localhost:8080/login 直接访问登录页面，和WebConfiguration.addViewControllers 添加的路径匹配
                    .failureUrl("/login/index") //登录失败跳转链接，需要GET方法
                    .successForwardUrl("/login/success") // 登录成功之后跳转链接，需要post方法
                    .permitAll() // 登录页面，可以直接访问
                .and().logout() // http://localhost:8080/logout 直接退出
                    .permitAll(); // 注销请求可直接访问
    }
}
```

## 3. 用户实体类 实现 UserDetails 接口 
## 4. 实现 UserDetailsService 接口 

