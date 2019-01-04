package com.buxz.entity;

/**
 * Created by SQ_BXZ on 2019-01-04.
 * 服务端向浏览器广播消息参数
 */
public class WiselyResponse {
    private String message;

    public WiselyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
