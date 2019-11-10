package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwj.aop.Nullable;
import com.zwj.entity.User;
import com.zwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("registerController")
@Controller
public class RegisterController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("user")
    private User user;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param userJson POST方式自动封装成的json字符串
     */
    @ResponseBody
    @RequestMapping(value = "register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String register(@RequestBody @Nullable String userJson, HttpServletResponse response, Map<String,Object> map){

        JSONObject jsonObject = JSON.parseObject(userJson);
        String userName = (String)jsonObject.get("user_name");
        String password01 = (String)jsonObject.get("user_password01");
        String password02 = (String)jsonObject.get("user_password02");
        String userNickName = (String)jsonObject.get("user_nick_name");

        return userService.register(userName,password01,password02,userNickName);

    }
}
