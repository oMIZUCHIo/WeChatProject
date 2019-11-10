package com.zwj.mapper;

import com.zwj.entity.UserGroups;

import java.util.List;
import java.util.Map;

public interface UserGroupsMapper {

    //新增群聊
    int addUserGroup(UserGroups userGroups);

    //删除群聊
    int deleteUserGroup(int userGroupId);

    //更换群里的职务（群主，管理员）
    int updatePositionInGroup(Map<String,String> map);

    //查询某人是否拥有某职务
    int queryPositionInGroup(Map<String,String> map);

    //删除某人的某职务
    int deletePositionInGroup(Map<String,String> map);

    //群主或管理员新建群公告
    int updateAnnouncement(Map<String,String> map);

    //查询某职务是否为空
    int queryIfNullPosition(Map<String,String> map);

    //根据ID搜索群聊信息
    UserGroups queryUserGroupsByUgId(int groupId);

    //更新群聊头像
    int updateUserGroupIcon(Map<String,String> map);

    //获得群聊头像URL
    String queryIconInUserGroup(int groupId);

    //获取用户的群聊列表
    List<UserGroups> queryUserGroup(int userId);

    //查询群聊内的用户数
    int queryUserNumInUserGroup(int groupId);

    //获得群聊内某职位上的用户ID
    int queryUserIdPosition(Map<String,String> map);
}
