package com.junit.caozhiou.sideproject.entity;

/**
 * Created by Administrator on 2017/1/17.
 */

public class LoginEvent {

    public static final String TYPE_LOGIN_SUCCESS="TYPE_LOGIN_SUCCESS";
    String type;

    public LoginEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
