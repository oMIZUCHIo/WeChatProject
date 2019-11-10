package com.zwj.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwj.entity.FriendGroups;
import com.zwj.entity.FriendsListVo;
import com.zwj.service.FriendGroupsService;
import com.zwj.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping("getFriendsController")
@Controller
public class GetFriendsController {

    @Autowired
    @Qualifier("friendsService")
    private FriendsService friendsService;

    @Autowired
    @Qualifier("friendsListVo")
    private FriendsListVo friendsListVo;

    @Autowired
    @Qualifier("friendGroupsService")
    private FriendGroupsService friendGroupsService;

    @Autowired
    private List<FriendGroups> friendGroupist;

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    public void setFriendsListVo(FriendsListVo friendsListVo) {
        this.friendsListVo = friendsListVo;
    }

    public void setFriendGroupsService(FriendGroupsService friendGroupsService) {
        this.friendGroupsService = friendGroupsService;
    }

    public void setFriendGroupist(List<FriendGroups> friendGroupist) {
        this.friendGroupist = friendGroupist;
    }

    /**
     * 获得用户的好友列表包括好友分组
     * @param userId 用户ID
     */
    @ResponseBody
    @RequestMapping(value = "getFriends",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String getFriends(@RequestParam int userId, HttpServletResponse response, Map<String,Object> map) {

        JSONObject jsonObject = new JSONObject();
        friendGroupist = friendGroupsService.queryAllGroup(userId);

        if (friendGroupist.size() == 0) {

            jsonObject.put("friendGroupist", "当前没有分组");

        } else {

            jsonObject.put("friendGroupist", friendGroupist);
        }

        friendsListVo = friendsService.queryFriends(userId);

        jsonObject.put("friendsList", friendsListVo);

        return jsonObject.toJSONString();
    }
}
