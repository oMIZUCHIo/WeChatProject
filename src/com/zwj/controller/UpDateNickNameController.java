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

@RequestMapping("upDateNickNameController")
@Controller
public class UpDateNickNameController {

    @Autowired
    @Qualifier("usersOfUserGroupService")
    private UsersOfUserGroupService usersOfUserGroupService;

    public void setUsersOfUserGroupService(UsersOfUserGroupService usersOfUserGroupService) {
        this.usersOfUserGroupService = usersOfUserGroupService;
    }

    /**
     * 群员更改昵称
     * @param userId 用户ID
     * @param groupId 群ID
     * @param nickName 昵称名
     */
    @ResponseBody
    @RequestMapping(value = "updateNikeName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateNikeName(@RequestParam int userId,@RequestParam int groupId,@RequestParam String nickName, HttpServletResponse response, Map<String,Object> map){

        return usersOfUserGroupService.updateNikeName(userId,groupId,nickName);
    }
}
