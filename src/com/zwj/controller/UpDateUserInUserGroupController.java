package com.zwj.controller;

import com.zwj.service.UsersOfUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("upDateUserInUserGroupController")
@Controller
public class UpDateUserInUserGroupController {

    @Autowired
    @Qualifier("usersOfUserGroupService")
    private UsersOfUserGroupService usersOfUserGroupService;

    public void setUsersOfUserGroupService(UsersOfUserGroupService usersOfUserGroupService) {
        this.usersOfUserGroupService = usersOfUserGroupService;
    }

    /**
     * 新增群里的用户
     * @param userId 用户ID
     * @param groupId 群ID
     */
    @ResponseBody
    @RequestMapping(value = "addUserInUserGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String addUserInUserGroup(@RequestParam int userId,@RequestParam int groupId, HttpServletResponse response, Map<String,Object> map){

        return usersOfUserGroupService.addUserInUserGroup(userId,groupId);
    }

    /**
     * 群员主动退群
     * @param userId 用户ID
     * @param groupId 群ID
     */
    @ResponseBody
    @RequestMapping(value = "deleteUserInUserGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String deleteUserInUserGroup(@RequestParam int userId,@RequestParam int groupId, HttpServletResponse response, Map<String,Object> map){

        return usersOfUserGroupService.deleteUserInUserGroup(userId,groupId);
    }

    /**
     * 群主踢除群里的某位群员
     * @param userId 群员ID
     * @param groupId 群ID
     * @param loginUserId 当前登录用户ID
     */
    @ResponseBody
    @RequestMapping(value = "adminDeleteUserInUserGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String adminDeleteUserInUserGroup(@RequestParam int loginUserId,@RequestParam int groupId,@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        return usersOfUserGroupService.adminDeleteUserInUserGroup(loginUserId,groupId,userId);
    }



}
