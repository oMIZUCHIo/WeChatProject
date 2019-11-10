package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

//封装好友在当前登录用户的分组
@Component(value = "friendsVo")
public class FriendsVo {

    private User user;           //当前登录用户
    private Friends friends;     //好友信息

    public FriendsVo(User user, Friends friends) {
        this.user = user;
        this.friends = friends;
    }

    public FriendsVo() {
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Friends getFriends() {
        return friends;
    }

    public User getUser() {
        return user;
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
