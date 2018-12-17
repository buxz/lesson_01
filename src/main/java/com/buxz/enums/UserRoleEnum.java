package com.buxz.enums;

public enum UserRoleEnum {
    admin("超级管理员"),
    user("普通用户");
    private String value;

    UserRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
