package com.zwj.entity;

import org.springframework.stereotype.Component;

import java.util.List;

//用于区别展示给前端的好友信息集合的封装类（已验证，未通过本人验证，用户已查看过但还未同意的各类型好友）
@Component("friendsListVo")
public class FriendsListVo {

    private int newFriendsNum;            //只有有未查看过的新好友的类型才有该属性
    private List<FriendsVo> friendsList;  //该类型对应的好友信息集合

    public FriendsListVo(int newFriendsNum, List<FriendsVo> friendsList) {
        this.newFriendsNum = newFriendsNum;
        this.friendsList = friendsList;
    }

    public FriendsListVo() {
    }

    public void setFriendsList(List<FriendsVo> friendsList) {
        this.friendsList = friendsList;
    }

    public List<FriendsVo> getFriendsList() {
        return friendsList;
    }

    public void setNewFriendsNum(int newFriendsNum) {
        this.newFriendsNum = newFriendsNum;
    }

    public int getNewFriendsNum() {
        return newFriendsNum;
    }

    @Override
    public String toString() {
        return "FriendsListVo{" +
                ", newFriendsNum=" + newFriendsNum +
                ", friendsList=" + friendsList +
                '}';
    }
}
