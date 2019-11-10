package com.zwj.entity;

import org.springframework.stereotype.Component;

import java.util.List;

//封装用户在单个群聊中所有未接收信息的类
@Component("groupChatNoReceiveMsgs")
public class GroupChatNoReceiveMsgs {

    private int ug_id;          //群ID
    private String ug_name;     //群名称
    private String ug_icon;     //群头像URL
    private int noReceiveNum;   //未接收信息数
    private String lastContent; //最后一条信息

    List<GroupChatReceiveMsgsVo> groupChatReceiveMsgsVoList;

    public GroupChatNoReceiveMsgs(int ug_id, String ug_name, String ug_icon, int noReceiveNum, String lastContent, List<GroupChatReceiveMsgsVo> groupChatReceiveMsgsVoList) {
        this.ug_id = ug_id;
        this.ug_name = ug_name;
        this.ug_icon = ug_icon;
        this.noReceiveNum = noReceiveNum;
        this.lastContent = lastContent;
        this.groupChatReceiveMsgsVoList = groupChatReceiveMsgsVoList;
    }

    public GroupChatNoReceiveMsgs() {
    }

    public void setUg_id(int ug_id) {
        this.ug_id = ug_id;
    }

    public void setUg_name(String ug_name) {
        this.ug_name = ug_name;
    }

    public void setUg_icon(String ug_icon) {
        this.ug_icon = ug_icon;
    }

    public void setNoReceiveNum(int noReceiveNum) {
        this.noReceiveNum = noReceiveNum;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    public void setGroupChatReceiveMsgsVoList(List<GroupChatReceiveMsgsVo> groupChatReceiveMsgsVoList) {
        this.groupChatReceiveMsgsVoList = groupChatReceiveMsgsVoList;
    }

    public int getUg_id() {
        return ug_id;
    }

    public String getUg_name() {
        return ug_name;
    }

    public String getUg_icon() {
        return ug_icon;
    }

    public int getNoReceiveNum() {
        return noReceiveNum;
    }

    public String getLastContent() {
        return lastContent;
    }

    public List<GroupChatReceiveMsgsVo> getGroupChatReceiveMsgsVoList() {
        return groupChatReceiveMsgsVoList;
    }

    @Override
    public String toString() {
        return "GroupChatNoReceiveMsgs{" +
                "ug_id=" + ug_id +
                ", ug_name='" + ug_name + '\'' +
                ", ug_icon='" + ug_icon + '\'' +
                ", noReceiveNum=" + noReceiveNum +
                ", lastContent='" + lastContent + '\'' +
                ", groupChatReceiveMsgsVoList=" + groupChatReceiveMsgsVoList +
                '}';
    }
}
