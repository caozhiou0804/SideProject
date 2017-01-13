package com.junit.caozhiou.sideproject.entity;

import java.io.Serializable;

/**
 * 作者：lubote on 2016/6/27 16:40
 * 邮箱：nj.caozhiou@dhjt.com
 */

public class BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
