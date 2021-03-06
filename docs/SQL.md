### 整合MySQL/Jpa/druid

1. 添加依赖

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		
2. 或者可以通过在项目初始化的过程中，加入jpa/mysql 的依赖项
3. 新建 application.yml 
    
    
    spring:
      datasource:
        url: jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&serverTimezone=UTC
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: root
        password: root
      jpa:
        database: mysql
        show-sql: true
4. sql文件见t_user.sql
5. 创建实体类 UserEntity
    1. 注意 : 主键生成策略为 （@GeneratedValue(strategy = GenerationType.IDENTITY)）
6. 创建JPA 
    
    
    public interface UserJPA extends
            JpaRepository<UserEntity, Long>,
            JpaSpecificationExecutor<UserEntity>,
            Serializable {
    }
    
7. controller 见 HelloController
8. 开启druid监控

添加依赖
    
    
    		<dependency>
    			<groupId>com.alibaba</groupId>
    			<artifactId>druid</artifactId>
    			<version>RELEASE</version>
    		</dependency>

application.yml配置

    spring:
      datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&serverTimezone=UTC
        username: root
        password: root
        #最大活跃数
        maxactive: 20
        #初始化数量
        initialSize: 1
        #最大连接等待超时时间
        maxWait: 60000
        #打开PSCache，并且指定每个连接PSCache的大小
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        #通过connectionProperties属性来打开mergeSql功能；慢SQL记录
        #connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
        minIdle: 1
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: select 1 from dual
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        #配置监控统计拦截的filters，去掉后监控界面sql将无法统计,'wall'用于防火墙
        filters: stat, wall, log4j
      jpa:
         properties:
           hibernate:
             show_sql: true
             format_sql: true


开启配置 DruidConfiguration
   
### SpringDataJPA 

#### 自定义sql
1. 使用@Query 自定义查询SQL ，
2. 使用@Query + @Modifying , 实现数据的增删改
3. 自定义的增删改语句，需要添加@Transaction 开启事务


      @Transactional
        @Modifying
        @Query(value = "DELETE  from t_user where t_name = ?1 AND t_pwd = ?2",nativeQuery = true)
        void deleteQuery(String name, String pwd);


#### 自定义BaseRepository
1. 继承JpaRepository 实现接口复用
2. 需要添加@NoRepositoryBean, 注解如果配置在继承了JpaRepository接口以及其他SpringDataJpa内部的接口的子接口时，子接口不被作为一个Repository创建代理实现类。


    @NoRepositoryBean
    public interface BaseRepository<T,PK extends Serializable> extends JpaRepository<T,PK> {
    
    }
    
#### 分页查询

       @RequestMapping("/cutPage")
        public List<UserEntity> cutPage(int page){
           UserEntity userEntity = new UserEntity();
           userEntity.setSize(2);
           userEntity.setPage(page==0?1:page);
           userEntity.setSord("desc");
           // 设置排序对象参数
           Sort sort = new Sort(Sort.Direction.DESC, userEntity.getSidx());
           // 创建分页对象
           PageRequest pageRequest = new PageRequest(userEntity.getPage()-1,userEntity.getSize(),sort);
           // 执行分页查询
           return userJPA.findAll(pageRequest).getContent();
        }


    
    
    










    
