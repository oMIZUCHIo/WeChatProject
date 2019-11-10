package com.zwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zwj.entity.FriendGroups;
import com.zwj.entity.FriendsVo;
import com.zwj.entity.User;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.FriendGroupsMapper;
import com.zwj.mapper.FriendsMapper;
import com.zwj.mapper.UserMapper;
import com.zwj.service.FriendGroupsService;
import com.zwj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service(value = "friendGroupsService")
public class FriendGroupsServiceImpl implements FriendGroupsService {

    @Autowired
    @Qualifier("friendGroupsMapper")
    private FriendGroupsMapper friendGroupsMapper;

    @Autowired
    @Qualifier("friendGroups")
    private FriendGroups friendGroups;

    @Autowired
    @Qualifier("friendsMapper")
    private FriendsMapper friendsMapper;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private List<User> userList ;

    @Autowired
    private List<FriendsVo> friendsVoList;

    //@Autowired
    private HashMap<String,Integer> map = new HashMap<>();

    private HashMap<String,String> map02 = new HashMap<>();

    public void setFriendGroupsMapper(FriendGroupsMapper friendGroupsMapper) {
        this.friendGroupsMapper = friendGroupsMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setFriendGroups(FriendGroups friendGroups) {
        this.friendGroups = friendGroups;
    }

    public void setFriendsMapper(FriendsMapper friendsMapper) {
        this.friendsMapper = friendsMapper;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setFriendsVoList(List<FriendsVo> friendsVoList) {
        this.friendsVoList = friendsVoList;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    //新增用户分组
    @Override
    public String addFriendGroup(String groupName,int userId) {

        JSONObject jsonObject = new JSONObject();

        friendGroups.setFg_name(groupName);
        friendGroups.setSet_user_id(userId);

        if(friendGroupsMapper.addFriendGroup(friendGroups) > 0){
            jsonObject.put("groupId",friendGroups.getFg_id());
            jsonObject.put("description","操作成功");
        }else{
            throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
        }
        return jsonObject.toJSONString();
    }

    //删除用户分组
    @Override
    public String deleteFriendGroup(int groupId, int userId) {

        //先删除分组绑定的用户，即将分组中的用户移至默认分组
        map.put("user_id",userId);
        map.put("friend_group_id",groupId);
        friendsMapper.deleteAllFriendInGroup(map);

        //再删除分组
        friendGroups.setSet_user_id(userId);
        friendGroups.setFg_id(groupId);
        int rowNum2 = friendGroupsMapper.deleteFriendGroup(friendGroups);

        return StringUtil.returnDefaultResult( rowNum2 > 0);
    }

    //查询用户的所有分组集合
    @Override
    public List<FriendGroups> queryAllGroup(int userId) {

        return friendGroupsMapper.queryAllGroup(userId);

    }

    //查询分组中的所有好友基本信息集合
    @Override
    public List<User> queryFriendsInGroup(int userId, int groupId) {

        friendsVoList.clear();
        map.put("user_id",userId);
        map.put("friend_type_id",1);

        for(FriendsVo friendsVo : friendsMapper.queryFriendsByType(map)){
            if(friendsVo != null  && friendsVo.getUser() != null){
                friendsVoList.add(friendsVo);
            }
        }

        //清楚userList中的空对象
        userList.clear();
        for(FriendsVo friendsVo : friendsVoList){
            if(friendsVo != null && friendsVo.getUser() != null){
                if(friendsVo.getFriends().getFriend_group_id() == groupId){
                    userList.add(friendsVo.getUser());
                }
            }
        }
        return userList;
    }

    //移动用户分组
    @Override
    public String updateFriendInGroup(int userId,int friendId,int groupId) {

        map.put("user_id",userId);
        map.put("friend_id",friendId);

        if(groupId == 0){
            map.put("friend_group_id",null);
        }else{
            map.put("friend_group_id",groupId);
        }

        return StringUtil.returnDefaultResult(friendsMapper.updateFriendInGroup(map) > 0);
    }

    //更新分组名称
    @Override
    public String updateFriendGroupName(int groupId, String groupName) {

        map02.put("fg_name",groupName);
        map02.put("fg_id",groupId + "");

        return StringUtil.returnDefaultResult(friendGroupsMapper.updateFriendGroupName(map02) > 0);
    }

}
