package com.zwj.controller;

import com.alibaba.fastjson.JSON;
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

@RequestMapping("upDateFriendTypeController")
@Controller
public class UpDateFriendTypeController {

    @Autowired
    @Qualifier("friendsService")
    private FriendsService friendsService;

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    /**
     * 用户向某人邀请好友
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    @ResponseBody
    @RequestMapping(value = "wannaBeFriend",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String wannaBeFriend(@RequestParam int userId, @RequestParam int friendId, HttpServletResponse response, Map<String,Object> map){

        return friendsService.wannaBeFriend(userId, friendId);
    }

    /**
     * 用户查看新好友后将这些好友设为已查看过的新好友
     * @param userId 用户ID
     * @param friendIdList 新好友ID集合
     */
    @ResponseBody
    @RequestMapping(value = "beExaminedFriends",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String beExaminedFriends(@RequestParam int userId, @RequestParam(value = "friendIdList",required = false)String friendIdList, HttpServletResponse response, Map<String,Object> map){

        return friendsService.beExaminedFriends(userId, JSON.parseArray(friendIdList,Integer.class));
    }

    /**
     * 用户拒绝某人的好友申请
     * @param userId 用户ID
     * @param friendId 新好友ID
     */
    @ResponseBody
    @RequestMapping(value = "refuseBeFriend",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String refuseBeFriend(@RequestParam int userId, @RequestParam int friendId, HttpServletResponse response, Map<String,Object> map){

        return friendsService.refuseBeFriend(userId, friendId);
    }
}
