package com.junit.caozhiou.sideproject.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */

public class UserListBean extends BaseBean{

    private List<UserDataBean> data;

    public List<UserDataBean> getData() {
        return data;
    }

    public void setData(List<UserDataBean> data) {
        this.data = data;
    }
}
