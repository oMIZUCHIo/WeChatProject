package com.zwj.service;

import com.zwj.entity.UserGroupVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserGroupsService {

    //新建群聊
    String addUserGroup(int userId, String groupName, List<Integer> friendIdList);

    //只有群主有解散群权力
    String deleteUserGroup(int userGroupId,int userId);

    //更换群主（一个群必须至少有一个群主）
    String updateAdminInGroup(int loginUserId,int ugId,int userId);

    //删除群里的某位管理员
    String deleteManagerInGroup(int ugId,int userId);

    //新增群里的管理员
    String addManagerInGroup(int ugId,int userId);

    //群主或管理员新建群公告
    String updateAnnouncement(int ugId,int userId,String announcement);

    //获得群中的所有用户基本信息集合以及群聊基本信息
    UserGroupVo queryUserGroupsVo(int groupId);

    //更新群聊头像
    String updateUserGroupIcon(int userId, int groupId, MultipartFile userGroupIcon);

    //获取用户的群聊列表
    String queryUserGroup(int userId);
}
