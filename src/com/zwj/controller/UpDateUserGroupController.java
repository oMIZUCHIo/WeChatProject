package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.zwj.service.UserGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("upDateUserGroupController")
@Controller
public class UpDateUserGroupController {

    @Autowired
    @Qualifier("userGroupsService")
    private UserGroupsService userGroupsService;

    public void setUserGroupsService(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }


    /**
     * 新建群聊
     * @param userId 用户ID
     * @param groupName 分组名
     * @param friendIdList 好友ID集合
     */
    @ResponseBody
    @RequestMapping(value = "addUserGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String addUserGroup(@RequestParam int userId,@RequestParam String groupName, @RequestParam(value = "friendIdList",required = false) String friendIdList, HttpServletResponse response, Map<String,Object> map){

        System.out.println(userId);
        System.out.println(friendIdList);

        return userGroupsService.addUserGroup(userId,groupName, JSON.parseArray(friendIdList,Integer.class));
    }


    /**
     * 只有群主有解散群权力
     * @param userId 用户ID
     * @param groupId 分组ID
     */
    @ResponseBody
    @RequestMapping(value = "deleteUserGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String deleteUserGroup(@RequestParam int groupId,@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

       return userGroupsService.deleteUserGroup(groupId,userId);
    }
}
