# 使用Lombok简化编码
> 简化了类的getter/setter/toString等方法的编码，但是也一定上降低了阅读力
## 1. 使用步骤
1. 引入依赖
    ```html
  	<dependency>
  		<groupId>org.projectlombok</groupId>
  		<artifactId>lombok</artifactId>
  		<version>RELEASE</version>
  	</dependency>

    ```
2. IDea 引入插件
> File > Settings > Plugins > Browse repositories... > 输入lombok，插件就会被自动检索出来，

3. 在目标类，使用注解
```java

package com.buxz.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data // 该注解 包含@Getter/@Setter/@ToString 三个注解
@Getter // 重写getter 方法
@Setter // 重写setter 方法
@ToString // 重写toSting方法
@AllArgsConstructor // 全参数构造器
@NoArgsConstructor // 空构造器
@Slf4j // 给该类添加日志组件的支持
public class UserEntityLombok {

    //名称
    private String name;
    //年龄
    private int age;
    //家庭住址
    private String address;

}

```
