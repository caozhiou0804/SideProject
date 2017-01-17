package com.junit.caozhiou.sideproject.entity;

import android.app.Activity;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-12
 * Time: 14:23
 * Description:个人中心设置选项实体
 */
public class PersonalLeftData extends BaseBean {

    private int icon_res;
    private String settingTitle;
    private Class<? extends Activity> desActivity;

    public PersonalLeftData(int icon_res, String settingTitle, Class<? extends Activity> desActivity) {
        this.icon_res = icon_res;
        this.settingTitle = settingTitle;
        this.desActivity = desActivity;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public String getSettingTitle() {
        return settingTitle;
    }

    public void setSettingTitle(String settingTitle) {
        this.settingTitle = settingTitle;
    }

    public Class<? extends Activity> getDesActivity() {
        return desActivity;
    }

    public void setDesActivity(Class<? extends Activity> desActivity) {
        this.desActivity = desActivity;
    }
}
