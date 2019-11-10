package com.zwj.mapper;

import com.zwj.entity.FriendGroups;

import java.util.List;
import java.util.Map;

//@Repository(value = "friendGroupsMapper")
public interface FriendGroupsMapper {

    //新增用户分组
    int addFriendGroup(FriendGroups friendGroups);

    //查询某用户的所有好友分组名
    List<FriendGroups> queryAllGroup(int userId);

    //删除用户分组
    int deleteFriendGroup(FriendGroups friendGroups);

    //移动用户分组
    int updateFriendGroupName(Map<String,String> map);
}
