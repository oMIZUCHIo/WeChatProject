package com.zwj.mapper;

import com.zwj.entity.User;

import java.util.List;
import java.util.Map;

//@Repository(value = "userMapper")
public interface UserMapper {

    User login(String userName);

    int register(User user);

    //通过用户名查询用户ID
    int queryUserIdByUserName(String userName);

    //用户登录系统时刷新用户状态码
    int updateUserStatus(User user);

    //根据用户名模糊查询相似用户基本信息集合
    List<User> queryLikedUser(Map<String,Object> map);

    //通过用户ID查询用户信息
    User queryUserByUserId(int userId);

    //根据用户ID获得用户头像URL地址
    String queryIconByUserId(int userId);

    //修改用户昵称
    int updateNickName(User user);

    //修改用户性别
    int updateSex(User user);

    //修改用户生日
    int updateBirthday(User user);

    //修改用户个性签名
    int updatePersonSignature(User user);

    //根据用户ID获取用户名和用户昵称
    User queryUserNameAndNickById(int userId);

    //更新用户头像
    int updateUserIcon(User user);

    //根据用户名查询用户
    User queryUserByUserName(String userName);

    //查询用户登录状态
    int queryUserStatus(int userId);

}
