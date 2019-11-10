package com.zwj.entity;

import org.springframework.stereotype.Component;

@Component("groupChatReceiveMsgs")
public class GroupChatReceiveMsgs {

    private int gcrm_id;    //主键ID
    private int to_user_id; //接收用户ID
    private int send_msg_id;//发送信息ID
    private int status;     //信息状态
    private int ug_id;      //群ID

    public GroupChatReceiveMsgs(int gcrm_id, int to_user_id, int send_msg_id, int status) {
        this.gcrm_id = gcrm_id;
        this.to_user_id = to_user_id;
        this.send_msg_id = send_msg_id;
        this.status = status;
    }

    public GroupChatReceiveMsgs() {
    }

    public void setGcrm_id(int gcrm_id) {
        this.gcrm_id = gcrm_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public void setSend_msg_id(int send_msg_id) {
        this.send_msg_id = send_msg_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUg_id(int ug_id) {
        this.ug_id = ug_id;
    }

    public int getGcrm_id() {
        return gcrm_id;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public int getStatus() {
        return status;
    }

    public int getSend_msg_id() {
        return send_msg_id;
    }

    public int getUg_id() {
        return ug_id;
    }

    @Override
    public String toString() {
        return "GroupChatReceiveMsgs{" +
                "gcrm_id=" + gcrm_id +
                ", to_user_id=" + to_user_id +
                ", send_msg_id=" + send_msg_id +
                ", status=" + status +
                ", ug_id=" + ug_id +
                '}';
    }
}
