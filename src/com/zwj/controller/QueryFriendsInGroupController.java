package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.zwj.entity.FriendsVo;
import com.zwj.entity.User;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.service.FriendGroupsService;
import com.zwj.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping("queryFriendsInGroupController")
@Controller
public class QueryFriendsInGroupController {

    @Autowired
    @Qualifier("friendsService")
    private FriendsService friendsService;

    @Autowired
    @Qualifier("friendGroupsService")
    private FriendGroupsService friendGroupsService;

    @Autowired
    private List<User> userList;

    @Autowired
    List<FriendsVo> friendsList;

    public void setFriendGroupsService(FriendGroupsService friendGroupsService) {
        this.friendGroupsService = friendGroupsService;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    /**
     * 获得用户的某分组中的所有好友信息集合
     * @param userId 用户ID
     * @param groupId 分组ID
     */
    @ResponseBody
    @RequestMapping(value = "queryFriendsInGroup",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String queryFriendsInGroup(@RequestParam int userId,@RequestParam int groupId, HttpServletRequest request, HttpServletResponse response, Map<String,Object> map){

        userList = friendGroupsService.queryFriendsInGroup(userId,groupId);

        if(userList.size() == 0){

            throw new MyRuntimeException(ErrorCodeEnum.FRIEND_GROUP_EMPTY);
        }else{

            map.put("userList",userList);
            map.put("userListJson", JSON.toJSONString(userList));
            return JSON.toJSONString(userList);
        }

       // return "friend_group";
    }
}
