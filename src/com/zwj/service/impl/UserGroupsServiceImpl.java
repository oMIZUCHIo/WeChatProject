package com.zwj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwj.entity.UserGroupVo;
import com.zwj.entity.UserGroups;
import com.zwj.entity.UsersOfUserGroup;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.*;
import com.zwj.service.UserGroupsService;
import com.zwj.util.DateUtil;
import com.zwj.util.FileUtil;
import com.zwj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userGroupsService")
public class UserGroupsServiceImpl implements UserGroupsService {

    @Autowired
    @Qualifier("userGroupsMapper")
    private UserGroupsMapper userGroupsMapper;

    @Autowired
    @Qualifier("usersOfUserGroupMapper")
    private UsersOfUserGroupMapper usersOfUserGroupMapper;

    @Autowired
    @Qualifier("contentMapper")
    private ContentMapper contentMapper;

    @Autowired
    @Qualifier("groupChatSendMsgsMapper")
    private GroupChatSendMsgsMapper groupChatSendMsgsMapper;

    @Autowired
    @Qualifier("groupChatReceiveMsgsMapper")
    private GroupChatReceiveMsgsMapper groupChatReceiveMsgsMapper;

    @Autowired
    @Qualifier("userGroups")
    private UserGroups userGroups;

    @Autowired
    @Qualifier("userGroupVo")
    private UserGroupVo userGroupVo;

    @Autowired
    @Qualifier("usersOfUserGroup")
    private UsersOfUserGroup usersOfUserGroup;

    private Map<String,String> map = new HashMap<>();

    @Autowired
    private List<UserGroups> userGroupsList;

    public void setUserGroupsMapper(UserGroupsMapper userGroupsMapper) {
        this.userGroupsMapper = userGroupsMapper;
    }

    public void setUsersOfUserGroupMapper(UsersOfUserGroupMapper usersOfUserGroupMapper) {
        this.usersOfUserGroupMapper = usersOfUserGroupMapper;
    }

    public void setUsersOfUserGroup(UsersOfUserGroup usersOfUserGroup) {
        this.usersOfUserGroup = usersOfUserGroup;
    }

    public void setUserGroups(UserGroups userGroups) {
        this.userGroups = userGroups;
    }

    public void setUserGroupVo(UserGroupVo userGroupVo) {
        this.userGroupVo = userGroupVo;
    }

    //新建群聊
    @Override
    public String addUserGroup(int userId, String groupName,List<Integer> friendIdList) {

        userGroups.setAdmin_id(userId);
        userGroups.setUg_name(groupName);
        userGroups.setSet_up_time(DateUtil.getNowTime());

        JSONObject jsonObject = new JSONObject();

        //获得项目在Tomcat上的资源路径
        String resProjectPath = FileUtil.getResProjectPath();

        //新注册用户头像为默认使用头像
        userGroups.setUg_icon("\\WeChatProjectResourse\\UserGroupIcon\\userGroupDefault.png");

        //将建群用户作为群主
        userGroups.setAdmin_id(userId);

        if(userGroupsMapper.addUserGroup(userGroups) > 0){

            usersOfUserGroup.setUg_id(userGroups.getUg_id());
            usersOfUserGroup.setUser_id(userId);

            int result = 0;

            //将建群用户添加为群员
            if(usersOfUserGroupMapper.addUserInUserGroup(usersOfUserGroup) > 0){

                new File(resProjectPath + "\\WeChatProjectResourse\\UserGroupFile\\" + userGroups.getUg_id()).mkdir();

                //用户在建群时未选择好友则直接返回成功
                if(friendIdList == null){
                    jsonObject.put("groupId",userGroups.getUg_id());
                    jsonObject.put("description","操作成功");
                }else{
                    if(friendIdList.size() == 0){
                        jsonObject.put("groupId",userGroups.getUg_id());
                        jsonObject.put("description","操作成功");
                    }else{
                        //将选中的好友逐个加入群聊中
                        for(int friendId : friendIdList){

                            usersOfUserGroup.setUser_id(friendId);
                            if(usersOfUserGroupMapper.addUserInUserGroup(usersOfUserGroup)  <= 0)
                                result ++;
                        }
                        if(result == 0){
                            jsonObject.put("groupId",userGroups.getUg_id());
                            jsonObject.put("description","操作成功");
                        }else{
                            throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
                        }
                    }
                }
                return jsonObject.toJSONString();
            }
        }
        throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
    }


    //只有群主有解散群权力
    @Override
    public String deleteUserGroup(int userGroupId,int userId) {

        map.clear();
        map.put("ug_id","" + userGroupId);
        map.put("position_name","admin_id");
        map.put("position_id","" + userId);

        //查询该用户是否为该群的群主
        if(userGroupsMapper.queryPositionInGroup(map) > 0){

            //删除群中的群聊信息
            int rowNum01 = contentMapper.deleteContent(userGroupId);
            int rowNum02 = groupChatSendMsgsMapper.deleteGroupSendMsg(userGroupId);
            int rowNum03 = groupChatReceiveMsgsMapper.deleteGroupReceiveMsg(userGroupId);

            //删除群消息中的文件
            String resProjectPath = FileUtil.getResProjectPath();

            File file = new File(resProjectPath + "\\WeChatProjectResourse\\UserGroupFile\\" + userGroupId);
            if(file.exists()){
                file.delete();
            }

            //先删除该群中的所有用户
            int rowNum04 = usersOfUserGroupMapper.deleteAllUserInGroup(userGroupId);

            //再删除该群聊信息
            int rowNum05 = userGroupsMapper.deleteUserGroup(userGroupId);

            return StringUtil.returnDefaultResult(rowNum01 > 0 && rowNum02 > 0  && rowNum03 > 0 && rowNum04 > 0 && rowNum05 > 0);

        }else{

            throw new MyRuntimeException(ErrorCodeEnum.ONLY_ADMIN_ALLOW);
        }
    }

    //更换群主
    @Override
    public String updateAdminInGroup(int loginUserId,int ugId,int userId) {

        map.clear();
        map.put("ug_id","" + ugId);
        map.put("position_name","admin_id");
        map.put("position_id","" + loginUserId);

        //判断登录用户是否为群主
        if(userGroupsMapper.queryPositionInGroup(map) > 0){

            map.put("ug_id","" + ugId);
            map.put("position_name","admin_id");
            map.put("position_id","" + userId);

            return StringUtil.returnDefaultResult(userGroupsMapper.updatePositionInGroup(map) > 0);
        }else{

            throw new MyRuntimeException(ErrorCodeEnum.ONLY_ADMIN_ALLOW);
        }

    }

    //删除群里的某位管理员
    @Override
    public String deleteManagerInGroup(int ugId, int userId) {

        map.clear();
        map.put("ug_id","" + ugId);
        map.put("position_name","manager_1_id");
        map.put("position_id","" + userId);

        //判断用户是否为管理员1
        if(userGroupsMapper.queryPositionInGroup(map) == 0){

            map.put("position_name","manager_2_id");

            //判断用户是否为管理员2
            if(userGroupsMapper.queryPositionInGroup(map) == 0){

                throw new MyRuntimeException(ErrorCodeEnum.ISNOT_MANAAGER);

            }else{

                return StringUtil.returnDefaultResult(userGroupsMapper.deletePositionInGroup(map) > 0);
            }

        }else{

            return StringUtil.returnDefaultResult(userGroupsMapper.deletePositionInGroup(map) > 0);
        }
    }

    //新增群里的管理员
    @Override
    public String addManagerInGroup(int ugId, int userId) {

        map.clear();
        map.put("ug_id","" + ugId);
        map.put("position_name","manager_1_id");
        map.put("position_id","" + userId);

        //判断当前用户是否为管理员1
        if(userGroupsMapper.queryPositionInGroup(map) == 0){

            //当管理员1职务不为空时
            if(userGroupsMapper.queryIfNullPosition(map) > 0){

                map.put("position_name","manager_2_id");

                //判断当前用户是否为管理员2
                if(userGroupsMapper.queryPositionInGroup(map) == 0){

                    //当管理员2职务也不为空时
                    if(userGroupsMapper.queryIfNullPosition(map) > 0){

                        throw new MyRuntimeException(ErrorCodeEnum.ONLY_TWO_MANAAGER_ALLWO);

                    }else{
                        //当管理员2职务为空时,添加当前用户为管理员2
                        return StringUtil.returnDefaultResult(userGroupsMapper.updatePositionInGroup(map) > 0);
                    }

                }else{
                    //当前用户为管理员2
                    throw new MyRuntimeException(ErrorCodeEnum.HAS_BEAN_MANAAGER);
                }

            }else{
                //当管理员1职务为空时,添加当前用户为管理员1
                return StringUtil.returnDefaultResult(userGroupsMapper.updatePositionInGroup(map) > 0);
            }

        }else{
            //当前用户为管理员1
            throw new MyRuntimeException(ErrorCodeEnum.HAS_BEAN_MANAAGER);
        }
    }

    //群主或管理员新建群公告
    @Override
    public String updateAnnouncement(int ugId, int userId, String announcement) {

        map.clear();

        map.put("ug_id", "" + ugId);
        map.put("ug_announcement", announcement);

        map.put("position_name", "admin_id");
        map.put("position_id", "" + userId);

        //判断用户是否为群主
        if (userGroupsMapper.queryPositionInGroup(map) == 0) {

            map.put("position_name", "manager_1_id");

            //判断用户是否为管理员1
            if (userGroupsMapper.queryPositionInGroup(map) == 0) {

                map.put("position_name", "manager_2_id");

                //判断用户是否为管理员2
                if (userGroupsMapper.queryPositionInGroup(map) == 0) {

                    throw new MyRuntimeException(ErrorCodeEnum.ONLY_ADMIN_MANAAGER_ALLWO);

                }
            }
        }

        return StringUtil.returnDefaultResult(userGroupsMapper.updateAnnouncement(map) > 0);
    }

    //获得群中的所有用户基本信息集合以及群聊基本信息
    @Override
    public UserGroupVo queryUserGroupsVo(int groupId) {

        userGroupVo.setUserVoList(usersOfUserGroupMapper.getAllUserVoList(groupId));

        userGroups = userGroupsMapper.queryUserGroupsByUgId(groupId);
        userGroups.setUser_num(userGroupsMapper.queryUserNumInUserGroup(groupId));
        userGroupVo.setUserGroups(userGroups);

        return userGroupVo;
    }

    //更新群聊头像
    @Override
    public String updateUserGroupIcon(int userId, int groupId, MultipartFile userGroupIcon) {

        if (userGroupIcon.isEmpty()) {

            throw new MyRuntimeException(ErrorCodeEnum.ICON_EMPTY);

        } else {

            map.clear();
            map.put("ug_id","" + groupId);
            map.put("position_name","admin_id");
            map.put("position_id","" + userId);

            //当操作用户为群主时
            if(userGroupsMapper.queryPositionInGroup(map) > 0) {

                //jsp中上传的文件：file
                //将file上传到服务器中的 某一个硬盘文件中\
                String trueFileName = userGroupIcon.getOriginalFilename(); //上传的文件名
                String type = trueFileName.contains(".") ? trueFileName.substring(trueFileName.lastIndexOf(".") + 1, trueFileName.length()) : null;
                System.out.println("图片初始名称为：" + trueFileName + " 类型为：" + type);

                if (type != null) {
                    try {

                        //获取Tomcat服务器目录
                        String realPath = FileUtil.getResProjectPath();
                        System.out.println(realPath);

                        String oldPath = userGroupsMapper.queryIconInUserGroup(groupId);
                        String newPath;

                        if("\\WeChatProjectResourse\\UserGroupIcon\\userGroupDefault.png".equals(oldPath)){

                            //原图片为默认图片则新建群聊图片名上传
                            String iconName = DateUtil.getCurrentTimeMillis() + "Icon." + type;

                            // 设置存放图片文件的路径
                            newPath = realPath + "\\WeChatProjectResourse\\UserGroupIcon\\" + iconName;
                            System.out.println("存放图片文件的路径:" + newPath);

                            File newFile = new File(newPath);

                            userGroupIcon.transferTo(newFile);

                            System.out.println("文件成功上传到指定目录下");

                            map.clear();
                            map.put("ug_id",groupId + "");
                            map.put("ug_icon","\\WeChatProjectResourse\\UserGroupIcon\\" + iconName);

                            return StringUtil.returnDefaultResult(userGroupsMapper.updateUserGroupIcon(map) > 0);

                        }else{
                            //原图片为群聊之前设置的则删除原图并保留原图名（方便前端渲染）
                            newPath = oldPath;

                            System.out.println("存放图片文件的路径:" + newPath);

                            File newFile = new File(realPath + newPath);
                            if(newFile.exists()){
                                newFile.delete();
                            }
                            userGroupIcon.transferTo(newFile);

                            System.out.println("文件成功上传到指定目录下");

                            throw new MyRuntimeException(ErrorCodeEnum.SUCCESS);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    throw new MyRuntimeException(ErrorCodeEnum.ICON_TYPE_EMPTY);
                }
            }else{
                throw new MyRuntimeException(ErrorCodeEnum.ONLY_ADMIN_ALLOW);
            }
            throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
        }
    }

    //获取用户的群聊列表
    @Override
    public String queryUserGroup(int userId) {

        userGroupsList = userGroupsMapper.queryUserGroup(userId);

        if(userGroupsList.size() > 0){
            for(UserGroups userGroups : userGroupsList){
                userGroups.setUser_num(userGroupsMapper.queryUserNumInUserGroup(userGroups.getUg_id()));
            }
        }
        return JSON.toJSONString(userGroupsList);
    }

}
