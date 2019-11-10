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

@RequestMapping("queryAllUserInGroupController")
@Controller
public class QueryAllUserInGroupController {

    @Autowired
    @Qualifier("userGroupsService")
    private UserGroupsService userGroupsService;

    public void setUserGroupsService(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }

    /**
     * 获得群中的所有用户最基本信息集合以及群聊基本信息
     * @param groupId 群ID
     */
    @ResponseBody
    @RequestMapping(value = "queryUserGroupsVo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryUserGroupsVo(@RequestParam int groupId, HttpServletResponse response, Map<String, Object> map) {

        return JSON.toJSONString(userGroupsService.queryUserGroupsVo(groupId));
    }
}