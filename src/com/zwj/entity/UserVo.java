package com.zwj.entity;

import org.springframework.stereotype.Component;

//用于群聊展示用户的基本信息
@Component
public class UserVo{

    private int user_id;        //用户ID
    private String user_name;   //用户名称
    private String user_icon;   //用户头像URL
    private int user_status;    //用户登录状态
    private String nick_name;   //用户昵称

    public UserVo(int user_id, String user_name, String user_icon, int user_status, String nick_name) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_icon = user_icon;
        this.user_status = user_status;
        this.nick_name = nick_name;
    }

    public UserVo() {
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public int getUser_status() {
        return user_status;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
