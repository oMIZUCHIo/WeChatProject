package com.zwj.entity;

import org.springframework.stereotype.Component;

@Component(value = "userGroups")
public class UserGroups {

    private int ug_id;          //群ID
    private String ug_name;     //群名称
    private String set_up_time; //群建立时间
    private int admin_id;       //群主ID
    private int manager_1_id;   //管理员1ID
    private int manager_2_id;   //管理员2ID
    private String ug_icon;     //群头像
    private String ug_announcement;//群公告
    private int user_num;       //群用户数

    public UserGroups(int ug_id, String ug_name, String set_up_time, int admin_id, int manager_1_id, int manager_2_id, String ug_icon, String ug_announcement, int user_num) {
        this.ug_id = ug_id;
        this.ug_name = ug_name;
        this.set_up_time = set_up_time;
        this.admin_id = admin_id;
        this.manager_1_id = manager_1_id;
        this.manager_2_id = manager_2_id;
        this.ug_icon = ug_icon;
        this.ug_announcement = ug_announcement;
        this.user_num = user_num;
    }

    public UserGroups() {
    }

    public void setUg_id(int ug_id) {
        this.ug_id = ug_id;
    }

    public void setUg_name(String ug_name) {
        this.ug_name = ug_name;
    }

    public void setSet_up_time(String set_up_time) {
        this.set_up_time = set_up_time;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public void setManager_1_id(int manager_1_id) {
        this.manager_1_id = manager_1_id;
    }

    public void setManager_2_id(int manager_2_id) {
        this.manager_2_id = manager_2_id;
    }

    public void setUg_icon(String ug_icon) {
        this.ug_icon = ug_icon;
    }

    public void setUg_announcement(String ug_announcement) {
        this.ug_announcement = ug_announcement;
    }

    public int getUg_id() {
        return ug_id;
    }

    public String getUg_name() {
        return ug_name;
    }

    public String getSet_up_time() {
        return set_up_time;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public int getManager_1_id() {
        return manager_1_id;
    }

    public int getManager_2_id() {
        return manager_2_id;
    }

    public String getUg_icon() {
        return ug_icon;
    }

    public String getUg_announcement() {
        return ug_announcement;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    public int getUser_num() {
        return user_num;
    }
}
