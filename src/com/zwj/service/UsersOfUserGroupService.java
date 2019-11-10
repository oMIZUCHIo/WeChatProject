package com.zwj.service;

import java.util.List;

public interface UsersOfUserGroupService {

    //新增群里的用户
    String addUserInUserGroup(int userId,int ugId);

    //群主踢除群里的某位群员
    String adminDeleteUserInUserGroup(int loginUserId,int ugId,int userId);

    //群员主动退群
    String deleteUserInUserGroup(int userId,int ugId);

    //群员更改昵称
    String updateNikeName(int userId,int ugId,String nikeName);

    //根据群聊ID获得群聊中的所有用户ID集合
    List<Integer> queryUserIdsInGroup(int groupId);

    //获得用户在群聊内的昵称
    String queryNickNameInUserGroup(int userId,int groupId);
}
