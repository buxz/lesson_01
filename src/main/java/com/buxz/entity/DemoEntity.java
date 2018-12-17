package com.buxz.entity;

import com.buxz.validator.FlagValidator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

public class DemoEntity implements Serializable
{
    @NotBlank // 非空
    @Length(min = 2,max = 10) // 长度在2-10
    private String name;

    @Min(value = 1) // 最小为1
    private int age;

    @NotBlank // 非空
    @Email // 邮箱格式
    private String mail;

    // 需要验证 flag字段内容仅为 1，2，3
    @FlagValidator(values = "1,2,3")
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
