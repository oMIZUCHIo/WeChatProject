package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

@Component(value = "user")
public class User {

    private int user_id;        //用户ID
    private String user_name;   //用户名
    private String user_password;//用户密码
    private String user_icon;   //用户头像URL
    private String user_nick_name;//用户昵称
    private String sex;         //用户性别
    private String birthday;    //用户生日
    private String person_signature;//用户个性签名
    private int user_status;    //用户登录状态
    private String login_time;  //用户最近一次登录时间

    public User(int user_id, String user_name, String user_password, String user_icon, String user_nick_name, String sex, String birthday, String person_signature, int user_status, String login_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_icon = user_icon;
        this.user_nick_name = user_nick_name;
        this.sex = sex;
        this.birthday = birthday;
        this.person_signature = person_signature;
        this.user_status = user_status;
        this.login_time = login_time;
    }

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public int getUser_status() {
        return user_status;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPerson_signature(String person_signature) {
        this.person_signature = person_signature;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPerson_signature() {
        return person_signature;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_icon='" + user_icon + '\'' +
                ", user_nick_name='" + user_nick_name + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", person_signature='" + person_signature + '\'' +
                ", user_status=" + user_status +
                ", login_time='" + login_time + '\'' +
                '}';
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
