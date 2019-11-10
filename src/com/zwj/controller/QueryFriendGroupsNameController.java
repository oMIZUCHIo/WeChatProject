package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.zwj.entity.FriendGroups;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.service.FriendGroupsService;
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

@RequestMapping("queryFriendGroupsNameController")
@Controller
public class QueryFriendGroupsNameController {

    @Autowired
    @Qualifier("friendGroupsService")
    private FriendGroupsService friendGroupsService;

    @Autowired
    private List<FriendGroups> friendGroupNameList;

    public void setFriendGroupsService(FriendGroupsService friendGroupsService) {
        this.friendGroupsService = friendGroupsService;
    }

    public void setFriendGroupNameList(List<FriendGroups> friendGroupNameList) {
        this.friendGroupNameList = friendGroupNameList;
    }

    /**
     * 获得用户的所有分组信息
     * @param userId 用户ID
     */
    @ResponseBody
    @RequestMapping(value = "queryFriendGroupsName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String queryFriendGroupsName(@RequestParam int userId, HttpServletResponse response, Map<String,Object> map){

        friendGroupNameList = friendGroupsService.queryAllGroup(userId);

        if(friendGroupNameList.size() == 0){

            throw new MyRuntimeException(ErrorCodeEnum.NOEXIST_FRIEND_GROUP);

        }else {

            return JSON.toJSONString(friendGroupNameList);
        }
        //return "friend_group";
    }
}
