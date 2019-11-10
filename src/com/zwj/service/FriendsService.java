package com.zwj.service;

import com.zwj.entity.FriendsListVo;

import java.util.List;

public interface FriendsService {

    //通过用户名获取其好友集合
    FriendsListVo queryFriends(int userId);

    //新增好友（即同意某人的好友申请）
    String addFriend(int userId,int friendId);

    //删除好友
    String deleteFriend(int userId,int friendId);

    //更改对好友的昵称标签
    String updateForFriendNickName(int userId,int friendId,String nickName);

    //用户向某人邀请好友
    String wannaBeFriend(int userId,int friendId);

    //用户查看新好友后将这些好友设为已查看过的新好友
    String beExaminedFriends(int userId,  List<Integer> friendIdList);

    //用户拒绝某人的好友申请
    String refuseBeFriend(int userId,int friendId);

    //判断两用户间是否为好友
    boolean isFriends(int userId,int friendId);
}
