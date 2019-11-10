package com.zwj.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

@Component(value = "content")
public class Content {

    private int content_id;     //主键ID
    private String content;     //信息内容
    private int content_type;   //信息类型
    private String file_name;   //文件名（非图片文件时）
    private String file_size;   //文件大小（非图片文件时）

    public Content(int content_id, String content, int content_type) {
        this.content_id = content_id;
        this.content = content;
        this.content_type = content_type;
    }

    public Content() {
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getContent_id() {
        return content_id;
    }

    public String getContent() {
        return content;
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
        return "Content{" +
                "content_id=" + content_id +
                ", content='" + content + '\'' +
                ", content_type=" + content_type +
                '}';
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }
}
