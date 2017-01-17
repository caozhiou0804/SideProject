package com.junit.caozhiou.sideproject.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/16.
 */

public class GetuiBean implements Serializable {

    /**
     * is_read : false
     * message_content : 内容
     * creator_date : 2017-01-16
     * message_title : 标题
     * id : 23567889000095
     */

    private String is_read;
    private String message_content;
    private String creator_date;
    private String message_title;
    private String id;

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getCreator_date() {
        return creator_date;
    }

    public void setCreator_date(String creator_date) {
        this.creator_date = creator_date;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
