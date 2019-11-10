package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

@Component(value = "friends")
public class Friends {

    private int f_id;                   //主键ID
    private int user_id;                //用户ID
    private int friend_id;              //好友ID
    private int friend_type_id;         //好友类型
    private int friend_group_id;        //好友所在分组ID
    private String for_friend_nick_name;//用户对好友的昵称

    public Friends(int f_id, int user_id, int friend_id, int friend_type_id, int friend_group_id, String for_friend_nick_name) {
        this.f_id = f_id;
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.friend_type_id = friend_type_id;
        this.friend_group_id = friend_group_id;
        this.for_friend_nick_name = for_friend_nick_name;
    }

    public Friends() {
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public void setFriend_type_id(int friend_type_id) {
        this.friend_type_id = friend_type_id;
    }

    public void setFriend_group_id(int friend_group_id) {
        this.friend_group_id = friend_group_id;
    }

    public int getF_id() {
        return f_id;
    }

    public void setFor_friend_nick_name(String for_friend_nick_name) {
        this.for_friend_nick_name = for_friend_nick_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public int getFriend_type_id() {
        return friend_type_id;
    }

    public int getFriend_group_id() {
        return friend_group_id;
    }

    public String getFor_friend_nick_name() {
        return for_friend_nick_name;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "f_id=" + f_id +
                ", user_id=" + user_id +
                ", friend_id=" + friend_id +
                ", friend_type_id=" + friend_type_id +
                ", friend_group_id=" + friend_group_id +
                '}';
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
