package com.zwj.service;

import com.zwj.entity.FriendGroups;
import com.zwj.entity.User;

import java.util.List;

public interface FriendGroupsService {

    //新增用户分组
    String addFriendGroup(String groupName,int userId);

    //删除用户分组
    String deleteFriendGroup(int groupId,int userId);

    //查询用户的所有分组集合
    List<FriendGroups> queryAllGroup(int userId);

    //查询分组中的所有好友基本信息集合
    List<User> queryFriendsInGroup(int userId,int groupId);

    //更改好友的分组
    String updateFriendInGroup(int userId,int friendId,int groupId);

    //更新分组名称
    String updateFriendGroupName(int groupId,String groupName);

}
