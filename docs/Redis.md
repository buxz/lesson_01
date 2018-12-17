# springboot 整合Redis
## 启动Redis
1. 下载    
 https://github.com/MicrosoftArchive/redis/releases
2. 安装
直接解压 即可 
3. 启动   
在redis解压目录下 使用cmd 执行 
```text
 redis-server.exe redis.windows.conf
```
4. 修改端口号
- redis 数据库默认的端口号是 6379
如果已经被其他程序占用，可以修改 redis.windos.conf 
- 位置 
```text
# Accept connections on the specified port, default is 6379 (IANA #815344).
# If port 0 is specified Redis will not listen on a TCP socket.
port 6379

```
## 配置项目
1. 添加依赖
```text
  <!-- SpringBoot内部的缓存配置 -->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
  </dependency>
  <!-- redis缓存 -->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-redis</artifactId>
      <version>RELEASE</version>
  </dependency>
```
2. 配置数据库   
修改application.yml文件
```text
#    配置Redis数据库链接

spring:
  redis:
    host: 127.0.0.1
    port: 6379
    pool:
      max-active: 20
      max-wait: 60000
      max-idle: 20
      min-idle: 1
    database: 0 # 默认是索引为 0的数据库

```
3. 配置CacheManager
- 新建 RedisCongfiguratin
4. 使用Redis
- 新建 UserService,在内部添加Redis支持
 ```java
@Service
@CacheConfig(cacheNames= "user-bxz")
public class UserService {
    @Autowired
    private UserJPA userJPA;

    @Cacheable
    public List<UserEntity> list(){
        return userJPA.findAll();
    }
}

```
- 在controller内部使用添加redis支持的Service 即可使用Redis

5. 自定义Key
- 修改 RedisConfiguration ，
- 继承 CachingConfigurerSupport
- 重写 keyGenerator()
## 常用命令
Cmd命令进入我们的Redis解压目录，并执行redis-cli.exe应用程序

命令|释义
---|---
flushdb | 清空当前数据库
select [index] | 选择索引数据库。eg. select 1
del [key] | 删除一条指定key的值
keys * | 查看数据库内所有的key
flushall | 清空所有数据库
quit | 退出客户端链接


