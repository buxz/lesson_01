### 配置Logback 

1. SpringBoot内部集成了LogBack所以我们不需要添加任何依赖
2. 默认配置只有Info及以上级别才可以输出
3.     Logger logger = LoggerFactory.getLogger(HelloController.class);

### 修改LogBack配置 
1. logback读取配置文件步骤：
    1) classpath下查找文件logback-test.xml
    2) 查找logback.xml
    3) LogBack 用 BasicConfiguration自动对自己进行最小化配置
2. 具体配置信息见 logback.xml
3. 在application.yml 配置
    
    
    # 配置日志的级别或者某个包/类里面的日志
    logging:
      level:
        com.buxz.controller.HelloController: 'error'
        
4. 在application.yml配置文件内，'off ' 或其他属性 ，必须添加双引号，否则不会生效。
    
