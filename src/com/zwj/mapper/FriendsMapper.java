package com.zwj.mapper;

import com.zwj.entity.Friends;
import com.zwj.entity.FriendsVo;

import java.util.HashMap;
import java.util.List;

//@Repository(value = "friendsMapper")
public interface FriendsMapper {

    //通过用户名获取其好友信息集合
    List<FriendsVo> queryFriends(int userId);

    //通过用户名和好友类别获取好友信息
    List<FriendsVo> queryFriendsByType(HashMap<String,Integer>map);

    //新增好友
    int addFriend(Friends friends);

    //删除好友
    int deleteFriend(HashMap<String,Integer>map);

    //删除某分组下的所有好友（删除整个分组时）
    int deleteAllFriendInGroup(HashMap<String,Integer> map);

    //更改好友的分组
    int updateFriendInGroup(HashMap<String,Integer> map);

    //判断两用户是否为好友
    int isFriend(HashMap<String,Integer> map);

    //更改对好友的昵称标签
    int updateForFriendNickName(Friends friends);

    //更新两用户间关系
    int updateFriendType(Friends friends);

    //判断两用户之前是否有过申请好友等记录
    int hasWannaBeFriend(HashMap<String,Integer> map);
}
