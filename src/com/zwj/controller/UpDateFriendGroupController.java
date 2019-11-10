package com.zwj.controller;

import com.zwj.service.FriendGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("upDateFriendGroupController")
@Controller
public class UpDateFriendGroupController {

    @Autowired
    @Qualifier("friendGroupsService")
    private FriendGroupsService friendGroupsService;

    public void setFriendGroupsService(FriendGroupsService friendGroupsService) {
        this.friendGroupsService = friendGroupsService;
    }

    /**
     * 用户新建好友分组
     * @param userId 用户ID
     * @param groupName 分组名称
     */
    @ResponseBody
    @RequestMapping(value = "addFriendGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String addFriendGroup(@RequestParam String groupName,@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        return friendGroupsService.addFriendGroup(groupName,userId);

      //  return "friend_group";
    }

    /**
     * 用户删除好友分组
     * @param userId 用户ID
     * @param groupId 分组ID
     */
    @ResponseBody
    @RequestMapping(value = "deleteFriendGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String deleteFriendGroup(@RequestParam int groupId,@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        return friendGroupsService.deleteFriendGroup(groupId,userId);
    }


    /**
     * 用户更改好友分组名称
     * @param groupId 用户ID
     * @param groupName 分组名
     */
    @ResponseBody
    @RequestMapping(value = "updateFriendGroupName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateFriendGroupName(@RequestParam int groupId,@RequestParam String groupName, HttpServletResponse response, Map<String,Object> map){

        return friendGroupsService.updateFriendGroupName(groupId,groupName);
    }

}
