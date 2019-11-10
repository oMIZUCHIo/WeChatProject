package com.zwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zwj.entity.User;
import com.zwj.entity.UserAndRole;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.RoleMapper;
import com.zwj.mapper.UserMapper;
import com.zwj.service.UserService;
import com.zwj.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("roleMapper")
    private RoleMapper roleMapper;

    @Autowired
    @Qualifier("userAndRole")
    private UserAndRole userAndRole;

    @Autowired
    @Qualifier("user")
    private User user;

    @Autowired
    @Qualifier("user")
    private User loginUser;

    @Autowired
    private List<User> userList;

    private Map<String,Object> map = new HashMap<>();

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public void setUserAndRole(UserAndRole userAndRole) {
        this.userAndRole = userAndRole;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String login(String userName, String password) {

        //判空
        StringUtil.ifNull(userName,"userName");
        StringUtil.ifNull(password,"password");

        JSONObject jsonObject = new JSONObject();

        loginUser = userMapper.login(userName);

            if (loginUser != null) {

                //判断用户是否已登录，只能单点登录
                if(loginUser.getUser_status() == 1){

                   throw new MyRuntimeException(ErrorCodeEnum.USER_HAS_LOGIN);
                }else{

                    //判断用户密码是否正确
                    if (loginUser.getUser_password().equals(encryptionUtil.md5Encode(password))) {

                        jsonObject.put("description", "操作成功");
                        jsonObject.put("loginUser", loginUser);

                        String jwt = null;
                        try {

                            //登录成功返回token
                            jwt = JWTUtil.creatToken(loginUser.getUser_id());
                            jsonObject.put("token",jwt);
                            System.out.println("token:"+jwt);

                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new MyRuntimeException("token产生失败");
                        }

                    } else {

                        throw new MyRuntimeException(ErrorCodeEnum.PASSWORD_ERROR);
                    }
                }
            } else {
                throw new MyRuntimeException(ErrorCodeEnum.USER_NOEXIST);
            }

        return jsonObject.toJSONString();
    }

    //@Transactional(propagation= Propagation.REQUIRED)
    @Override
    public String register(String userName, String password01, String password02,String userNickName) {

        StringUtil.ifNull(userName,"userName");
        StringUtil.ifNull(password01,"password01");
        StringUtil.ifNull(password02,"password02");
        StringUtil.ifNull(userNickName,"userNickName");

        //通过用户输入的用户名查看用户是否存在
        if (userMapper.queryUserIdByUserName(userName) != 0) {

            throw new MyRuntimeException(ErrorCodeEnum.USER_EXIST);

        } else if (!password01.equals(password02)) {

            throw new MyRuntimeException(ErrorCodeEnum.PASSWORD_REPEAT);

        } else {

            user.setUser_name(userName);
            user.setUser_password(encryptionUtil.md5Encode(password01));

            if(userNickName != null && !"".equals(userNickName)){
                user.setUser_nick_name(userNickName);
            }

            //获得项目在Tomcat上的资源路径
            String resProjectPath = FileUtil.getResProjectPath();

            //新注册用户头像为默认使用头像
            user.setUser_icon("\\WeChatProjectResourse\\UserIcon\\singlePersonDefault.png");
            user.setUser_status(2);

            //新增用户
            int rowNum1 = userMapper.register(user);

            //联系用户与角色
            //通过用户名查询用户ID（以插入数据库）
            userAndRole.setUser_id(user.getUser_id());
            //注册时都为普通用户
            userAndRole.setRole_id(1);

            int rowNum2 = roleMapper.addUserAndRole(userAndRole);

            if(rowNum1 > 0 && rowNum2 > 0){

                new File(resProjectPath + "\\UserFile\\" + user.getUser_id()).mkdir();

                throw new MyRuntimeException(ErrorCodeEnum.SUCCESS);
            }else{
                throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
            }

        }
    }

    //用户登入系统时刷新用户状态
    @Override
    public boolean loginSystem(int userId) {

        user.setUser_id(userId);
        //状态码为1时用户登入系统
        user.setUser_status(1);
        //记录用户登入系统时间
        user.setLogin_time(DateUtil.getNowTime());

        return userMapper.updateUserStatus(user) > 0;
    }

    //用户登出系统时刷新用户状态码
    @Override
    public boolean existSystem(int userId) {

        user.setUser_id(userId);
        //状态码为2时用户下线
        user.setUser_status(2);
        user.setLogin_time(null);

        return userMapper.updateUserStatus(user) > 0;
    }

    @Override
    public List<User> queryLikedUserName(String name,int page,int capacity) {

        map.put("name",name);
        map.put("first",(page - 1) * capacity);
        map.put("final",(page * capacity - 1));

        return userMapper.queryLikedUser(map);
    }

    //修改用户昵称
    @Override
    public String updateNickName(int userId, String nickName) {

        user.setUser_id(userId);
        user.setUser_nick_name(nickName);

        return StringUtil.returnDefaultResult(userMapper.updateNickName(user) > 0);
    }

    //修改用户性别
    @Override
    public String updateSex(int userId, String sex) {

        user.setUser_id(userId);
        user.setSex(sex);

        return StringUtil.returnDefaultResult(userMapper.updateSex(user) > 0);
    }

    //修改用户生日
    @Override
    public String updateBirthday(int userId, String birthday) {

        user.setUser_id(userId);
        user.setBirthday(birthday);

        return StringUtil.returnDefaultResult(userMapper.updateBirthday(user) > 0);
    }

    //修改用户个性签名
    @Override
    public String updatePersonSignature(int userId, String personSignature) {

        user.setUser_id(userId);
        user.setPerson_signature(personSignature);

        return StringUtil.returnDefaultResult(userMapper.updatePersonSignature(user) > 0);
    }

    //根据用户ID获取用户对象
    @Override
    public User queryUserByUserId(int userId) {

        return userMapper.queryUserByUserId(userId);
    }

    //更新用户头像
    @Override
    public String updateUserIcon(int userId, MultipartFile userIcon) {

        if (userIcon.isEmpty()) {

            throw new MyRuntimeException(ErrorCodeEnum.ICON_EMPTY);

        } else {

            //jsp中上传的文件：file
            //将file上传到服务器中的 某一个硬盘文件中\
            String trueFileName = userIcon.getOriginalFilename(); //上传的文件名
            String type = trueFileName.contains(".") ? trueFileName.substring(trueFileName.lastIndexOf(".") + 1, trueFileName.length()) : null;
            System.out.println("图片初始名称为：" + trueFileName + " 类型为：" + type);

            if (type != null) {
                try {

                    //获取Tomcat服务器目录
                    String realPath = FileUtil.getResProjectPath();
                    System.out.println(realPath);

                    String oldPath = userMapper.queryIconByUserId(userId);
                    String newPath;

                    if("\\WeChatProjectResourse\\UserIcon\\singlePersonDefault.png".equals(oldPath)){

                        //原图片为默认图片则新建用户图片名上传
                        String iconName = DateUtil.getCurrentTimeMillis() + "Icon." + type;

                        // 设置存放图片文件的路径
                        newPath = realPath + "\\WeChatProjectResourse\\UserIcon\\" + iconName;
                        System.out.println("存放图片文件的路径:" + newPath);

                        File newFile = new File(newPath);

                        userIcon.transferTo(newFile);

                        System.out.println("文件成功上传到指定目录下");

                        user.setUser_icon("\\WeChatProjectResourse\\UserIcon\\" + iconName);
                        user.setUser_id(userId);

                        return StringUtil.returnDefaultResult(userMapper.updateUserIcon(user) > 0);
                    }else{
                        //原图片为用户之前设置的则删除原图并保留原图名（方便前端渲染）
                        newPath = oldPath;
                        System.out.println("存放图片文件的路径:" + newPath);

                        File newFile = new File(realPath + newPath);
                        if(newFile.exists()){
                            newFile.delete();
                        }
                        userIcon.transferTo(newFile);

                        System.out.println("文件成功上传到指定目录下");

                        throw new MyRuntimeException(ErrorCodeEnum.SUCCESS);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            throw new MyRuntimeException(ErrorCodeEnum.ICON_TYPE_EMPTY);
        }
    }

    //根据用户名查询用户
    @Override
    public User queryUserByUserName(String userName) {
        return userMapper.queryUserByUserName(userName);
    }
}
