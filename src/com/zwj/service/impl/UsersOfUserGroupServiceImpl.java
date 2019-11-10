package com.zwj.service.impl;

import com.zwj.entity.UsersOfUserGroup;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.UserGroupsMapper;
import com.zwj.mapper.UsersOfUserGroupMapper;
import com.zwj.service.UsersOfUserGroupService;
import com.zwj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("usersOfUserGroupService")
public class UsersOfUserGroupServiceImpl implements UsersOfUserGroupService {

    @Autowired
    @Qualifier("usersOfUserGroupMapper")
    private UsersOfUserGroupMapper usersOfUserGroupMapper;

    @Autowired
    @Qualifier("userGroupsMapper")
    private UserGroupsMapper userGroupsMapper;

    @Autowired
    @Qualifier("usersOfUserGroup")
    private UsersOfUserGroup usersOfUserGroup;

    private Map<String,String> map = new HashMap<>();

    public void setUsersOfUserGroupMapper(UsersOfUserGroupMapper usersOfUserGroupMapper) {
        this.usersOfUserGroupMapper = usersOfUserGroupMapper;
    }

    public void setUsersOfUserGroup(UsersOfUserGroup usersOfUserGroup) {
        this.usersOfUserGroup = usersOfUserGroup;
    }

    //新增群里的用户
    @Override
    public String addUserInUserGroup(int userId,int ugId){

        usersOfUserGroup.setUser_id(userId);
        usersOfUserGroup.setUg_id(ugId);

        return StringUtil.returnDefaultResult(usersOfUserGroupMapper.addUserInUserGroup(usersOfUserGroup) > 0);
    }

    //群主踢除群里的所有群员（解散群时使用）
    @Override
    public String adminDeleteUserInUserGroup(int loginUserId,int ugId,int userId) {

        map.clear();

        map.put("ug_id", "" + ugId);
        map.put("position_name", "admin_id");
        map.put("position_id", "" + loginUserId);

        //当前登录为群主时执行踢人操作
        if(userGroupsMapper.queryPositionInGroup(map) > 0){

            usersOfUserGroup.setUser_id(userId);
            usersOfUserGroup.setUg_id(ugId);

            return StringUtil.returnDefaultResult(usersOfUserGroupMapper.deleteUserInUserGroup(usersOfUserGroup) > 0);
        }else{

            throw new MyRuntimeException(ErrorCodeEnum.ONLY_ADMIN_ALLOW);
        }

    }

    //群员主动退群
    @Override
    public String deleteUserInUserGroup(int userId, int ugId) {

        map.clear();

        map.put("ug_id", "" + ugId);
        map.put("position_name", "admin_id");
        map.put("position_id", "" + userId);

        //当退群用户为群主时
        if(userGroupsMapper.queryPositionInGroup(map) > 0){

            usersOfUserGroup.setUg_id(ugId);
            usersOfUserGroup.setUser_id(userId);

            int userIdPosition;

            //先将群主从群中删除
            if(usersOfUserGroupMapper.deleteUserInUserGroup(usersOfUserGroup) > 0){

                map.put("position_name", "manager_1_id");

                //获得群聊中管理员1的ID
                userIdPosition = userGroupsMapper.queryUserIdPosition(map);

                //当群聊的管理员1未设置用户时
                if(userIdPosition == 0){

                    map.put("position_name", "manager_2_id");

                    //获得群聊中管理员2的ID
                    userIdPosition = userGroupsMapper.queryUserIdPosition(map);

                    //当群聊的管理员2未设置用户时
                    if(userIdPosition == 0){

                        //获得群中一名用户ID（先加入群聊的用户）
                        userIdPosition = usersOfUserGroupMapper.queryUserIdInGroup(ugId);
                    }
                }
                    //设置新的群主
                    map.put("position_name", "admin_id");
                    map.put("position_id",userIdPosition + "");
                    return StringUtil.returnDefaultResult(userGroupsMapper.updatePositionInGroup(map) > 0);
            }else{
                throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
            }

            //当退群的是管理员时
        }else{

            map.put("position_name", "manager_1_id");

            if(userGroupsMapper.queryPositionInGroup(map) > 0){
                //将群聊中的该管理员设为空
                userGroupsMapper.deletePositionInGroup(map);
            }else{

                map.put("position_name", "manager_2_id");

                if(userGroupsMapper.queryPositionInGroup(map) > 0){
                    //将群聊中的该管理员设为空
                    userGroupsMapper.deletePositionInGroup(map);
                }
            }
        }
        usersOfUserGroup.setUg_id(ugId);
        usersOfUserGroup.setUser_id(userId);

        return StringUtil.returnDefaultResult(usersOfUserGroupMapper.deleteUserInUserGroup(usersOfUserGroup) > 0);
    }

    //群员更改昵称
    @Override
    public String updateNikeName(int userId, int ugId,String nikeName) {

        usersOfUserGroup.setUser_id(userId);
        usersOfUserGroup.setUg_id(ugId);
        usersOfUserGroup.setNick_name(nikeName);

        return StringUtil.returnDefaultResult(usersOfUserGroupMapper.updateNikeName(usersOfUserGroup) > 0);
    }

    //根据群聊ID获得群聊中的所有用户ID集合
    @Override
    public List<Integer> queryUserIdsInGroup(int groupId) {
        return usersOfUserGroupMapper.getUserIdsListInGroup(groupId);
    }

    //获得用户在群聊内的昵称
    @Override
    public String queryNickNameInUserGroup(int userId, int groupId) {

        usersOfUserGroup.setUg_id(groupId);
        usersOfUserGroup.setUser_id(userId);

        return usersOfUserGroupMapper.queryNickNameInGroup(usersOfUserGroup);
    }
}
