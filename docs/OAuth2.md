# SpringSecurity 整合 OAuth2 

- OAuth2在“客户端”与“服务提供商”之间，设置了一个授权层(authorization layer)
- “客户端”不能直接登录“服务提供商”，只能登录授权层，以此将用户与客户端分离。
- “客户端”登录需要OAuth提供的令牌，否则将提示认证失败而导致客户端无法访问服务

## OAuth2四种授权方式(本项目使用密码模式)

1. 授权码模式（authorization code）
- 相对其他三种来说是功能比较完整、流程最安全严谨的授权方式
- 通过客户端的后台服务器与服务提供商的认证服务器交互来完成
2. 简化模式（implicit）
- 不通过服务器端程序来完成，直接由浏览器发送请求获取令牌
- 令牌是完全暴露在浏览器中的，这种模式极力不推崇
3. 密码模式（resource owner password credentials）
- 客户端向授权服务器提供用户名、密码然后得到授权令牌
- 弊端，客户端需要存储用户输入的密码,但是对于用户来说信任度不高的平台是不可能让他们输入密码的

4. 客户端模式（client credentials）
- 客户端以自己的名义去授权服务器申请授权令牌，并不是完全意义上的授权

## 构建项目
### 1. 引入依赖
```html
        <!--SpringSecurity-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<!--SpringSecurityOAuth2-->
		<dependency>
			<groupId>org.springframework.security.oauth</groupId>
			<artifactId>spring-security-oauth2</artifactId>
		</dependency>
		
```
### 2. 自定义userDetailsService
```java
package com.buxz.service;

import com.buxz.entity.OAuth2Authority;
import com.buxz.entity.OAuth2User;
import com.buxz.exceptions.NewUserNotFoundException;
import com.buxz.jpa.OAuth2UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Component("oauth2UserDetailsService")
public class OAuth2UserDetailsService implements UserDetailsService
{
    @Autowired
    private OAuth2UserJPA userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        String lowercaseLogin = login.toLowerCase();

        OAuth2User userFromDatabase = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);

        if (userFromDatabase == null) {
            throw new NewUserNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        //获取用户的所有权限并且SpringSecurity需要的集合
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (OAuth2Authority authority : userFromDatabase.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        //返回一个SpringSecurity需要的用户对象
        return new org.springframework.security.core.userdetails.User(
                userFromDatabase.getUsername(),
                userFromDatabase.getPassword(),
                grantedAuthorities);

    }
}

```
### 3. 配置SpringSecurity
```java

package com.buxz.config;

import com.buxz.service.OAuth2UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@EnableWebSecurity
public class SpringSecurityOAuth2Configuration extends WebSecurityConfigurerAdapter
{
    //自定义UserDetailsService注入
    @Autowired
    private OAuth2UserDetailsService userDetailsService;

    //配置匹配用户时密码规则
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
    //配置全局设置
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //设置UserDetailsService以及密码规则
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    //排除/hello路径拦截
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/hello");
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    //开启全局方法拦截
    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    public static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }

    }
}

```
### 4. 模拟数据(applicaiton.properties)
```java

authentication.oauth.clientid=yuqiyu_home_pc
authentication.oauth.secret=yuqiyu_secret
authentication.oauth.tokenValidityInSeconds=600
# 这个配置的意思时，将我们的资源拦截的过滤器运行顺序放到第3个执行，也就是在oauth2的认证服务器后面执行
security.oauth2.resource.filter-order = 3
```
### 5. Oauth2 配置
> 配置安全资源服务器 
> 
```java
package com.buxz.config;

import com.buxz.enums.AuthorityEnum;
import com.buxz.natives.CustomAuthenticationEntryPoint;
import com.buxz.natives.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * OAuth2总配置类
 */
@Configuration
public class OAuth2Configuration {

    /**
     * 资源服务器的配置
     * @EnableResourceServer 注解来开启资源服务器
     * 因为整合SpringSecurity的缘故，我们需要配置登出时清空对应的access_token控制以及自定义401错误内容（authenticationEntryPoint），
     * 在配置类中我们排除了对/hello公开地址拦截以及/secure下的所有地址都必须授权才可以访问
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        // 自定义401错误码内容
        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        // 定义登出控制
        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                    .logout()
                    .logoutUrl("/oauth/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/hello/").permitAll()
                    .antMatchers("/secure/**").authenticated();

        }

    }

    /**
     * 开启OAuth2的验证服务器
     * EnvironmentAware（读取properties文件需要）接口
     * @EnableAuthorizationServer 注解开启了验证服务器
     * 使用SpringSecurityOAuth2内定义的JdbcStore来操作数据库中的Token
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

        private static final String ENV_OAUTH = "authentication.oauth.";
        private static final String PROP_CLIENTID = "clientid";
        private static final String PROP_SECRET = "secret";
        private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

        private RelaxedPropertyResolver propertyResolver;

        @Qualifier("dataSource")
        @Autowired
        private DataSource dataSource;

        @Bean
        public TokenStore tokenStore() {
            JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);
            return new JdbcTokenStore(dataSource);
        }

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(propertyResolver.getProperty(PROP_CLIENTID))
                    .scopes("read", "write")
                    .authorities(AuthorityEnum.ROLE_ADMIN.name(), AuthorityEnum.ROLE_USER.name())
                    .authorizedGrantTypes("password", "refresh_token")
                    .secret(propertyResolver.getProperty(PROP_SECRET))
                    .accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
        }

    }

}

```
### 6. 验证
> - 127.0.0.1:8080/hello 正常返回
> - 127.0.0.1:8080/secure 提示需要授权
> - 127.0.0.1:8080/oauth/token?username=admin&password=admin&grant_type=password 获取accesstoken
> - http://127.0.0.1:8080/secure?access_token=9ca7fd9b-1289-440b-b1a1-0303782f660e 正常访问


