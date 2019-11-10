package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

@Component(value = "friendGroups")
public class FriendGroups {

    private int fg_id;      //主键ID
    private String fg_name; //分组名
    private int set_user_id;//建立该分组的用户ID

    public FriendGroups(int fg_id, String fg_name, int set_user_id) {
        this.fg_id = fg_id;
        this.fg_name = fg_name;
        this.set_user_id = set_user_id;
    }

    public FriendGroups() {
    }

    public void setFg_id(int fg_id) {
        this.fg_id = fg_id;
    }

    public void setFg_name(String fg_name) {
        this.fg_name = fg_name;
    }

    public int getFg_id() {
        return fg_id;
    }

    public String getFg_name() {
        return fg_name;
    }

    public void setSet_user_id(int set_user_id) {
        this.set_user_id = set_user_id;
    }

    public int getSet_user_id() {
        return set_user_id;
    }

    @Override
    public String toString() {
        return "FriendGroups{" +
                "fg_id=" + fg_id +
                ", fg_name='" + fg_name + '\'' +
                ", set_user_id=" + set_user_id +
                '}';
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
