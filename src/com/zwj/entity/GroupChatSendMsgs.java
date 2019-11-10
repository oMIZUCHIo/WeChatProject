package com.zwj.entity;

import org.springframework.stereotype.Component;

//封装用户群聊发送信息
@Component("groupChatSendMsgs")
public class GroupChatSendMsgs {

    private int gcsm_id;        //主键ID
    private int from_user_id;   //发送信息用户ID
    private int content_id;     //内容ID
    private String send_time;   //发送时间
    private int ug_id;          //群ID

    public GroupChatSendMsgs(int gcsm_id, int from_user_id, int content_id, String send_time, int ug_id) {
        this.gcsm_id = gcsm_id;
        this.from_user_id = from_user_id;
        this.content_id = content_id;
        this.send_time = send_time;
        this.ug_id = ug_id;
    }

    public GroupChatSendMsgs() {
    }

    public void setGcsm_id(int gcsm_id) {
        this.gcsm_id = gcsm_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public void setUg_id(int ug_id) {
        this.ug_id = ug_id;
    }

    public int getGcsm_id() {
        return gcsm_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public int getContent_id() {
        return content_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public int getUg_id() {
        return ug_id;
    }

    @Override
    public String toString() {
        return "GroupChatSendMsgs{" +
                "gcsm_id=" + gcsm_id +
                ", from_user_id=" + from_user_id +
                ", content_id=" + content_id +
                ", send_time='" + send_time + '\'' +
                ", ug_id=" + ug_id +
                '}';
    }
}
