package com.zwj.entity;

import org.springframework.stereotype.Component;

//封装用户接收到的完整单条群聊信息
@Component("groupChatReceiveMsgsVo")
public class GroupChatReceiveMsgsVo {

    private int ug_id;                  //群聊ID
    private int from_user_id;           //发送该条信息用户ID
    private String from_user_name;      //该用户名称
    private String from_user_nick_name; //该用户昵称
    private String from_user_icon;      //该用户头像
    private String content;             //信息内容
    private int content_type;           //信息类型
    private String file_name;           //若信息类型为文件时的文件名
    private String file_size;              //若信息类型为文件时的文件大小
    private String send_time;           //该信息发送时间

    public GroupChatReceiveMsgsVo(int ug_id, int from_user_id, String from_user_nick_name, String from_user_icon, String content, int content_type, String send_time) {
        this.ug_id = ug_id;
        this.from_user_id = from_user_id;
        this.from_user_nick_name = from_user_nick_name;
        this.from_user_icon = from_user_icon;
        this.content = content;
        this.content_type = content_type;
        this.send_time = send_time;
    }

    public GroupChatReceiveMsgsVo() {
    }

    public void setUg_id(int ug_id) {
        this.ug_id = ug_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public void setFrom_user_nick_name(String from_user_nick_name) {
        this.from_user_nick_name = from_user_nick_name;
    }

    public void setFrom_user_icon(String from_user_icon) {
        this.from_user_icon = from_user_icon;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public int getUg_id() {
        return ug_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public String getFrom_user_nick_name() {
        return from_user_nick_name;
    }

    public String getFrom_user_icon() {
        return from_user_icon;
    }

    public String getContent() {
        return content;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getContent_type() {
        return content_type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_size() {
        return file_size;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    @Override
    public String toString() {
        return "GroupChatReceiveMsgsVo{" +
                "ug_id=" + ug_id +
                ", from_user_id=" + from_user_id +
                ", from_user_nick_name='" + from_user_nick_name + '\'' +
                ", from_user_icon='" + from_user_icon + '\'' +
                ", content='" + content + '\'' +
                ", content_type=" + content_type +
                ", send_time='" + send_time + '\'' +
                '}';
    }
}
