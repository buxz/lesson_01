package com.buxz.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data // 该注解 包含@Getter/@Setter/@ToString 三个注解
//@Getter // 重写getter 方法
//@Setter // 重写setter 方法
//@ToString // 重写toSting方法
@AllArgsConstructor // 全参数构造器
@NoArgsConstructor // 空构造器
@Slf4j // 给该类添加日志组件的支持
public class UserEntityLombok {

    //用户名
    private String name;
    // 密码
    private String password;
    //年龄
    private int age;
    //家庭住址
    private String address;

}
