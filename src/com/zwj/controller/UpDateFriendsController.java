package com.zwj.controller;

import com.zwj.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("upDateFriendsController")
@Controller
public class UpDateFriendsController {

    @Autowired
    @Qualifier("friendsService")
    private FriendsService friendsService;

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    /**
     * 用户新增好友（即同意好友申请）
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    @ResponseBody
    @RequestMapping(value = "addFriend",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String addFriend(@RequestParam int userId,@RequestParam int friendId, HttpServletResponse response, Map<String,Object> map){

        return friendsService.addFriend(userId,friendId);
       // return "get_friends";
    }

    /**
     * 用户删除好友
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    @ResponseBody
    @RequestMapping(value = "deleteFriend",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String deleteFriend(@RequestParam int userId,@RequestParam int friendId, HttpServletResponse response, Map<String,Object> map){

        return friendsService.deleteFriend(userId,friendId);
        //return "get_friends";
    }

    /**
     * 更改对好友的昵称标签
     * @param userId 用户ID
     * @param friendId 好友ID
     * @param nickName 昵称
     */
    @ResponseBody
    @RequestMapping(value = "updateForFriendNickName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateForFriendNickName(@RequestParam int userId,@RequestParam int friendId,@RequestParam String nickName, HttpServletResponse response, Map<String,Object> map){

        return friendsService.updateForFriendNickName(userId,friendId,nickName);
        //return "get_friends";
    }

}
