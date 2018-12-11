### 整合MySQL/Jpa

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

    
    
    










    
