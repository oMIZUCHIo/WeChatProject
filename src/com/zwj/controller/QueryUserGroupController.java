package com.zwj.controller;

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

@RequestMapping("queryUserGroupController")
@Controller
public class QueryUserGroupController {

    @Autowired
    @Qualifier("userGroupsService")
    private UserGroupsService userGroupsService;

    public void setUserGroupsService(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }

    /**
     * 获得用户的群聊列表
     * @param userId 用户ID
     */
    @ResponseBody
    @RequestMapping(value = "queryUserGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String queryUserGroup(@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        return userGroupsService.queryUserGroup(userId);

    }
}
