package com.zwj.service.impl;

import com.zwj.entity.Friends;
import com.zwj.entity.FriendsListVo;
import com.zwj.entity.FriendsVo;
import com.zwj.entity.User;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.FriendsMapper;
import com.zwj.mapper.UserMapper;
import com.zwj.service.FriendsService;
import com.zwj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service(value = "friendsService")
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    @Qualifier("friendsMapper")
    private FriendsMapper friendsMapper;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    private List<FriendsVo> friendsVoList;

    @Autowired
    @Qualifier("friendsListVo")
    private FriendsListVo friendsListVo;

    @Autowired
    @Qualifier("user")
    private User user;

    @Autowired
    @Qualifier("friends")
    private Friends friends;

    //@Autowired
    private HashMap<String,Integer> map = new HashMap<>();

    public void setFriendsMapper(FriendsMapper friendsMapper) {
        this.friendsMapper = friendsMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    public void setFriendsListVo(FriendsListVo friendsListVo) {
        this.friendsListVo = friendsListVo;
    }

    public void setFriendsVoList(List<FriendsVo> friendsVoList) {
        this.friendsVoList = friendsVoList;
    }


    //通过用户名获取其好友集合（已验证）
    @Override
    public FriendsListVo queryFriends(int userId) {

        friendsVoList.clear();
        int newFriendsNum = 0;

        for(FriendsVo friendsVo : friendsMapper.queryFriends(userId)){
            if(friendsVo.getFriends().getFriend_type_id() != 0  && friendsVo.getUser() != null){

                if(friendsVo.getFriends().getFriend_type_id() == 2){
                    newFriendsNum ++;
                }
                friendsVoList.add(friendsVo);
            }
        }
        friendsListVo.setNewFriendsNum(newFriendsNum);
        friendsListVo.setFriendsList(friendsVoList);

        return friendsListVo;
    }


    //新增好友
    @Override
    public String addFriend(int userId, int friendId) {

        //先判断两用户是否已是好友
        map.put("user_id", userId);
        map.put("friend_id", friendId);
        if (friendsMapper.isFriend(map) > 0) {
            throw new MyRuntimeException(ErrorCodeEnum.HAS_BEAN_FRIEND);
        }
        friends.setUser_id(userId);
        friends.setFriend_id(friendId);
        friends.setFriend_type_id(1);

        int rowNum01 = friendsMapper.updateFriendType(friends);

        friends.setUser_id(friendId);
        friends.setFriend_id(userId);
        friends.setFriend_type_id(1);

        int rowNum02 = friendsMapper.updateFriendType(friends);

        return StringUtil.returnDefaultResult((rowNum01 > 0 && rowNum02 > 0));
    }

    //删除好友
    @Override
    public String deleteFriend(int userId, int friendId) {

        map.put("user_id",userId);
        map.put("friend_id",friendId);

        int rowNum01 = friendsMapper.deleteFriend(map);

        map.put("user_id",friendId);
        map.put("friend_id",userId);

        int rowNum02 = friendsMapper.deleteFriend(map);

        return StringUtil.returnDefaultResult(rowNum01 > 0 && rowNum02 > 0);
    }

    //更改对好友的昵称标签
    @Override
    public String updateForFriendNickName(int userId, int friendId, String nickName) {

        friends.setUser_id(userId);
        friends.setFriend_id(friendId);
        friends.setFor_friend_nick_name(nickName);

        return StringUtil.returnDefaultResult(friendsMapper.updateForFriendNickName(friends) > 0);
    }

    //用户向某人邀请好友
    @Override
    public String wannaBeFriend(int userId, int friendId) {

        map.put("user_id",userId);
        map.put("friend_id",friendId);
        map.put("friend_type_id",1);

        //两用户已是好友状态
        if(friendsMapper.isFriend(map) > 0){

            throw new MyRuntimeException(ErrorCodeEnum.HAS_BEAN_FRIEND);

            //曾有过邀请记录则在更新两用户间关系就行
        }else if(friendsMapper.hasWannaBeFriend(map) > 0){

            //4 ：已发送好友邀请
            friends.setUser_id(userId);
            friends.setFriend_id(friendId);
            friends.setFriend_type_id(4);

            int rowNum01 = friendsMapper.updateFriendType(friends);

            //2：未查看过的新好友
            friends.setUser_id(friendId);
            friends.setFriend_id(userId);
            friends.setFriend_type_id(2);

            int rowNum02 = friendsMapper.updateFriendType(friends);

            return StringUtil.returnDefaultResult(rowNum01 > 0 && rowNum02 > 0);

        }else{
            //不曾有过邀请记录则在数据库中要加入两行数据
            user = userMapper.queryUserNameAndNickById(friendId);

            //4 ：已发送好友邀请
            friends.setUser_id(userId);
            friends.setFriend_id(friendId);
            friends.setFriend_type_id(4);

            //默认首选用户的昵称，昵称为空则使用用户名
            if(user.getUser_nick_name() != null){

                friends.setFor_friend_nick_name(user.getUser_nick_name());
            }else{

                friends.setFor_friend_nick_name(user.getUser_name());
            }

            int rowNum1 = friendsMapper.addFriend(friends);

            //交换两用户好友对象，加入数据库
            //2：未查看过的新好友
            friends.setUser_id(friendId);
            friends.setFriend_id(userId);
            friends.setFriend_type_id(2);

            user = userMapper.queryUserNameAndNickById(userId);

            //默认首选用户的昵称，昵称为空则使用用户名
            if(user.getUser_nick_name() != null){

                friends.setFor_friend_nick_name(user.getUser_nick_name());
            }else{

                friends.setFor_friend_nick_name(user.getUser_name());
            }

            int rowNum2 = friendsMapper.addFriend(friends);

            return StringUtil.returnDefaultResult(rowNum1 > 0 && rowNum2 > 0);
        }

    }

    //用户查看新好友后将这些好友设为已查看过的新好友
    @Override
    public String beExaminedFriends(int userId,  List<Integer>  friendIdList) {

        friends.setUser_id(userId);
        friends.setFriend_type_id(3);
        int flag = 0;

        for (int friendId : friendIdList) {
            friends.setFriend_id(friendId);
            if (friendsMapper.updateFriendType(friends) <= 0) {
                flag++;
            }
        }

        return StringUtil.returnDefaultResult(flag <= 0);
    }

    //用户拒绝某人的好友申请
    @Override
    public String refuseBeFriend(int userId, int friendId) {

        //5 ：已拒绝好友邀请
        friends.setUser_id(userId);
        friends.setFriend_id(friendId);
        friends.setFriend_type_id(5);

        int rowNum01 = friendsMapper.updateFriendType(friends);

        //6 ：已被拒绝好友邀请
        friends.setUser_id(friendId);
        friends.setFriend_id(userId);
        friends.setFriend_type_id(6);

        int rowNum02 = friendsMapper.updateFriendType(friends);

        return StringUtil.returnDefaultResult(rowNum01 > 0 && rowNum02 > 0);
    }

    //判断两用户间是否为好友
    @Override
    public boolean isFriends(int userId, int friendId) {

        map.put("user_id",userId);
        map.put("friend_id",friendId);
        map.put("friend_type_id",1);

        return friendsMapper.isFriend(map) > 0;
    }

}
