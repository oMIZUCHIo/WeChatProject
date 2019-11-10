package com.zwj.mapper;

import com.zwj.entity.UserVo;
import com.zwj.entity.UsersOfUserGroup;

import java.util.List;

public interface UsersOfUserGroupMapper {

    //新增群里的用户
    int addUserInUserGroup(UsersOfUserGroup usersOfUserGroup);

    //踢除群里的群员
    int deleteUserInUserGroup(UsersOfUserGroup usersOfUserGroup);

    //删除某群里的所有群员
    int deleteAllUserInGroup(int ugId);

    //群员更改昵称
    int updateNikeName(UsersOfUserGroup usersOfUserGroup);

    //获得某群聊下的所有用户ID集合
    List<Integer> getUserIdsListInGroup(int ugId);

    //获得某用户加入的所有群聊ID集合
    List<Integer> getGroupIdsList(int userId);

    //获得某群聊下的所有用户在该群中的基本信息集合
    List<UserVo> getAllUserVoList(int ugId);

    //查询某用户在群聊中的昵称
    String queryNickNameInGroup(UsersOfUserGroup UsersOfUserGroup);

    //获得一位群员ID
    int queryUserIdInGroup(int ugId);
}
