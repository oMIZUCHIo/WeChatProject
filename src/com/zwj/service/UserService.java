package com.zwj.service;

import com.zwj.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    String login(String userName,String password);

    String register(String userName,String password01,String password02,String userNickName);

    //用户登入系统时刷新用户状态
    boolean loginSystem(int userId);

    //用户登出系统时刷新用户状态码
    boolean existSystem(int userId);

    List<User> queryLikedUserName(String name,int page,int capacity);

    //修改用户昵称
    String updateNickName(int userId,String nickName);

    //修改用户性别
    String updateSex(int userId,String sex);

    //修改用户生日
    String updateBirthday(int userId,String birthday);

    //修改用户个性签名
    String updatePersonSignature(int userId,String personSignature);

    //根据用户ID获取用户对象
    User queryUserByUserId(int userId);

    //更新用户头像
    String updateUserIcon(int userId, MultipartFile userIcon);

    //根据用户名查询用户
    User queryUserByUserName(String userName);
}
