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

@RequestMapping("upDateUserGroupPositionController")
@Controller
public class UpDateUserGroupPositionController {

    @Autowired
    @Qualifier("userGroupsService")
    private UserGroupsService userGroupsService;

    public void setUserGroupsService(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }

    /**
     * 更换群主（一个群必须至少有一个群主）
     * @param userId 新群主ID
     * @param groupId 群ID
     * @param loginUserId 当前登录用户ID
     */
    @ResponseBody
    @RequestMapping(value = "updateAdminInGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateAdminInGroup(@RequestParam int loginUserId,@RequestParam int groupId,@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        return userGroupsService.updateAdminInGroup(loginUserId,groupId,userId);
    }

    /**
     * 删除群里的某位管理员
     * @param userId 新管理员ID
     * @param groupId 群ID
     */
    @ResponseBody
    @RequestMapping(value = "deleteManagerInGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String deleteManagerInGroup(@RequestParam int groupId,@RequestParam int userId, HttpServletResponse response,  Map<String,Object> map){

        return userGroupsService.deleteManagerInGroup(groupId,userId);
    }


    /**
     * 新增群里的管理员
     * @param userId 新管理员ID
     * @param groupId 群ID
     */
    @ResponseBody
    @RequestMapping(value = "addManagerInGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String addManagerInGroup( @RequestParam int groupId,@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        return userGroupsService.addManagerInGroup(groupId,userId);
    }

}
