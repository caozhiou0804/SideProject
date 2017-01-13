package com.junit.caozhiou.sideproject.entity;

/**
 * Created by Administrator on 2017/1/5.
 */

public class CityBean extends BaseBean {
    private Object data;
    private int totalPage;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
