package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.zwj.entity.User;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.service.UserService;
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

@RequestMapping("queryLikedUserNameController")
@Controller
public class queryLikedUserNameController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private List<User> userList;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * 查询用户
     * @param name 搜索内容
     * @param page 页码
     * @param capacity 页面容量
     */
    @ResponseBody
    @RequestMapping(value = "queryLikedName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String queryLikedName(@RequestParam String name,@RequestParam int page,@RequestParam int capacity, HttpServletResponse response, Map<String,Object> map) {

            userList = userService.queryLikedUserName(name,page,capacity);

            if(userList.size() == 0){

                throw new MyRuntimeException(ErrorCodeEnum.EMPTY_RESULT);

            }else{

                return JSON.toJSONString(userList);
            }
       // return "user";
    }

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     */
    @ResponseBody
    @RequestMapping(value = "queryUserByUserName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String queryUserByUserName(@RequestParam String userName, HttpServletResponse response, Map<String,Object> map) {

        return JSON.toJSONString(userService.queryUserByUserName(userName));
    }

}
