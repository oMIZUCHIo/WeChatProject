package com.zwj.controller;

import com.zwj.service.UserGroupsService;
import com.zwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("updateIconController")
@Controller
public class UpdateIconController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("userGroupsService")
    private UserGroupsService userGroupsService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserGroupsService(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }

    /**
     * 更新单个用户头像
     * @param userId 用户ID
     * @param userIcon 图片
     */
    @ResponseBody
    @RequestMapping(value="updateUserIcon",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String updateUserIcon(@RequestParam int userId , @RequestParam(value = "userIcon", required = false) MultipartFile userIcon, HttpServletRequest request ) throws IOException {

        return userService.updateUserIcon(userId,userIcon);
    }

    /**
     * 更新群聊头像
     * @param userId 用户ID
     * @param userGroupIcon 图片
     */
    @ResponseBody
    @RequestMapping(value="updateUserGroupIcon",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String updateUserGroupIcon(@RequestParam int groupId,@RequestParam int userId, @RequestParam(value = "userGroupIcon", required = false) MultipartFile userGroupIcon,HttpServletRequest request ) throws IOException {

        return userGroupsService.updateUserGroupIcon(userId,groupId,userGroupIcon);
    }
}
