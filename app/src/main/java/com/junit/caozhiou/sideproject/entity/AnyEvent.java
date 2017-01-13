package com.junit.caozhiou.sideproject.entity;

/**
 * eventbus
 * Created by Administrator on 2017/1/11.
 */

public class AnyEvent {

    private String discribe;


    //构造函数
    public AnyEvent(String discribe) {
        this.discribe = discribe;
    }

    //set/get方法
    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }

    public String getDiscribe() {
        return discribe;
    }

}