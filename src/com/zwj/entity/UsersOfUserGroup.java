package com.zwj.entity;

import org.springframework.stereotype.Component;

@Component("usersOfUserGroup")
public class UsersOfUserGroup {

    private int uoug_id;    //主键ID
    private int user_id;    //用户ID
    private int ug_id;      //群ID
    private String nick_name;//用户在群中的昵称

    public UsersOfUserGroup(int uoug_id, int user_id, int ug_id, String nick_name) {
        this.uoug_id = uoug_id;
        this.user_id = user_id;
        this.ug_id = ug_id;
        this.nick_name = nick_name;
    }

    public UsersOfUserGroup() {
    }

    public void setUoug_id(int uoug_id) {
        this.uoug_id = uoug_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUg_id(int ug_id) {
        this.ug_id = ug_id;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getUoug_id() {
        return uoug_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getUg_id() {
        return ug_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    @Override
    public String toString() {
        return "UsersOfUserGroup{" +
                "uoug_id=" + uoug_id +
                ", user_id=" + user_id +
                ", ug_id=" + ug_id +
                ", nick_name='" + nick_name + '\'' +
                '}';
    }
}
