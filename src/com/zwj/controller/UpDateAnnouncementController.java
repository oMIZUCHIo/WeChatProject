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

@RequestMapping("upDateAnnouncementController")
@Controller
public class UpDateAnnouncementController {

    @Autowired
    @Qualifier("userGroupsService")
    private UserGroupsService userGroupsService;

    public void setUserGroupsService(UserGroupsService userGroupsService) {
        this.userGroupsService = userGroupsService;
    }

    /**
     * 群主或管理员新建群公告
     * @param userId 用户ID
     * @param groupId 群ID
     * @param announcement 新群公告
     */
    @ResponseBody
    @RequestMapping(value = "updateAnnouncement",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateAnnouncement(@RequestParam int userId,@RequestParam int groupId,@RequestParam String announcement, HttpServletResponse response, Map<String,Object> map){

        //map.put("msg01",userGroupsService.updateAnnouncement(groupId,userId,announcement));
        return userGroupsService.updateAnnouncement(groupId,userId,announcement);
    }
}
