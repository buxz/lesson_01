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
```java
/**
 * UserDetails  SpringSecurity验证框架内部提供的用户验证接口（我们下面需要用到UserEntity来完成自定义用户认证功能）
 */
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity implements Serializable,UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private Long id;

    @Column(name = "t_name")
    private String username;

    @Column(name = "t_age")
    private int age;

    @Column(name = "t_address")
    private String address;

    @Column(name = "t_pwd")
    private String password;

    // FetchType.LAZY 懒加载，加载一个实体时，定义懒加载的属性不会马上从数据库中加载。(默认加载方式)
    // FetchType.EAGER 急加载，加载一个实体时，定义急加载的属性会立即从数据库中加载。
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_user_role",
            joinColumns = {
                    @JoinColumn(name = "ur_user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ur_role_id")
            }
    )
    private List<RoleEntity> roles;

    /**
     * 将我们定义的角色列表添加到授权的列表内。
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<RoleEntity> roles = getRoles();
        for (RoleEntity role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getFlag()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 。。。省略其余getset方法
}


```
## 4. 实现 UserDetailsService 接口 
```java

/**
 * Created by SQ_BXZ on 2018-12-17.
 * @CacheConfig 该类参加缓存，如果方法内的@Cacheable没有添加key值，那么会自动使用cacheNames 配置参数并追加方法名
 * @Cacheable 配置方法的缓存参数，可自定义缓存的key以及value。
 *
 */
@Service
@CacheConfig(cacheNames= "user-bxz")
public class UserService implements UserDetailsService {

    @Autowired
    private UserJPA userJPA;


    @Cacheable
    public List<UserEntity> list(){
        return userJPA.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userJPA.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("未查询到用户："+username);
        }
        return user;
    }

}

```

