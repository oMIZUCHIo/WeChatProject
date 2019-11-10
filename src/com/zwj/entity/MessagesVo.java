package com.zwj.entity;

import org.springframework.stereotype.Component;

import java.util.List;

//用于在首页显示未接收的用户信息
@Component(value = "messagesVo")
public class MessagesVo {

    private int from_user_id;       //发送用户ID
    private String from_user_name;  //发送用户名称
    private String from_user_url;   //发送用户头像URL
    private int stick_to_top;       //是否为置顶
    private int from_user_status;   //发送用户当前登录状态
    private int no_receive_num;     //未接收信息
    private String last_content;    //最后一条信息内容

    private List<Messages> messagesList;

    public MessagesVo(int from_user_id, String from_user_name, String from_user_url, int stick_to_top, int from_user_status, int no_receive_num, String last_content, List<Messages> messagesList) {
        this.from_user_id = from_user_id;
        this.from_user_name = from_user_name;
        this.from_user_url = from_user_url;
        this.stick_to_top = stick_to_top;
        this.from_user_status = from_user_status;
        this.no_receive_num = no_receive_num;
        this.last_content = last_content;
        this.messagesList = messagesList;
    }

    public MessagesVo() {
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public void setFrom_user_url(String from_user_url) {
        this.from_user_url = from_user_url;
    }

    public void setStick_to_top(int stick_to_top) {
        this.stick_to_top = stick_to_top;
    }

    public void setFrom_user_status(int from_user_status) {
        this.from_user_status = from_user_status;
    }

    public void setNo_receive_num(int no_receive_num) {
        this.no_receive_num = no_receive_num;
    }

    public void setLast_content(String last_content) {
        this.last_content = last_content;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public String getFrom_user_url() {
        return from_user_url;
    }

    public int getStick_to_top() {
        return stick_to_top;
    }

    public int getFrom_user_status() {
        return from_user_status;
    }

    public int getNo_receive_num() {
        return no_receive_num;
    }

    public String getLast_content() {
        return last_content;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }
}
