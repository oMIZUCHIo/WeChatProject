package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

//用户与角色之间的关联
@Component(value = "userAndRole")
public class UserAndRole {

    private int ur_id;
    private int user_id;
    private int role_id;

    public UserAndRole(int ur_id, int user_id, int role_id) {
        this.ur_id = ur_id;
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public UserAndRole() {
    }

    public void setUr_id(int ur_id) {
        this.ur_id = ur_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getUr_id() {
        return ur_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    @Override
    public String toString() {
        return "UserAndRole{" +
                "ur_id=" + ur_id +
                ", user_id=" + user_id +
                ", role_id=" + role_id +
                '}';
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
