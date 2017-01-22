package com.junit.caozhiou.sideproject.entity;

/**
 * Created by Administrator on 2017/1/21.
 */

public class PersonInfoEvent {

    public static final String UPDATE_INFO = "update_info";
    private String type;

    private UserDataBean userDataBean;

    //构造函数
    public PersonInfoEvent(String type) {
        this.type = type;
    }

    public UserDataBean getUserDataBean() {
        return userDataBean;
    }

    public void setUserDataBean(UserDataBean userDataBean) {
        this.userDataBean = userDataBean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
