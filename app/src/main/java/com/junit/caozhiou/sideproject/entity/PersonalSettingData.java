package com.junit.caozhiou.sideproject.entity;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-12
 * Time: 14:23
 * Description:个人中心设置选项实体
 */
public class PersonalSettingData extends BaseEntity{

    private int icon_res;
    private String settingTitle;

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
}
