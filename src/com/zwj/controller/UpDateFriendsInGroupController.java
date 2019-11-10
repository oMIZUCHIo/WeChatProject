package com.zwj.controller;

import com.zwj.aop.Nullable;
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

@RequestMapping("upDateFriendsInGroupController")
@Controller
public class UpDateFriendsInGroupController {

    @Autowired
    @Qualifier("friendGroupsService")
    private FriendGroupsService friendGroupsService;

    public void setFriendGroupsService(FriendGroupsService friendGroupsService) {
        this.friendGroupsService = friendGroupsService;
    }

    /**
     * 移动好友的分组
     * @param userId 用户ID
     * @param friendId 好友ID
     * @param groupId 移去的分组ID（默认分组则为0）
     */
    @ResponseBody
    @RequestMapping(value = "updateFriendInGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateFriendInGroup(@RequestParam int userId, @RequestParam int friendId, @RequestParam @Nullable int groupId, HttpServletResponse response, Map<String,Object> map){

        return friendGroupsService.updateFriendInGroup(userId,friendId,groupId);
       // return "friend_group";
    }


}
