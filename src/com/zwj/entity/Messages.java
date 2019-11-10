package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

//封装用户单聊发送信息
@Component(value = "messages")
public class Messages {

    private int message_id;     //主键ID
    private int from_user_id;   //发送用户ID
    private String from_user_name;//发送用户名称
    private String from_user_url; //发送用户头像URL
    private int to_user_id;      //接收用户ID
    private int content_id;      //内容ID
    private String content;      //信息内容
    private int content_type;   //信息类型
    private String file_name;   //文件名称
    private String file_size;   //文件大小
    private String send_time;   //发送时间
    private int status;         //信息状态

    public Messages(int message_id, int from_user_id, String from_user_name, String from_user_url, int to_user_id, int content_id, String content, int content_type, String send_time, int status) {
        this.message_id = message_id;
        this.from_user_id = from_user_id;
        this.from_user_name = from_user_name;
        this.from_user_url = from_user_url;
        this.to_user_id = to_user_id;
        this.content_id = content_id;
        this.content = content;
        this.content_type = content_type;
        this.send_time = send_time;
        this.status = status;
    }

    public Messages() {
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public void setFrom_user_url(String from_user_url) {
        this.from_user_url = from_user_url;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getMessage_id() {
        return message_id;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public int getContent_id() {
        return content_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public int getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public String getFrom_user_url() {
        return from_user_url;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "message_id=" + message_id +
                ", from_user_id=" + from_user_id +
                ", from_user_name='" + from_user_name + '\'' +
                ", from_user_url='" + from_user_url + '\'' +
                ", to_user_id=" + to_user_id +
                ", content_id=" + content_id +
                ", content='" + content + '\'' +
                ", content_type=" + content_type +
                ", send_time='" + send_time + '\'' +
                ", status=" + status +
                '}';
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
