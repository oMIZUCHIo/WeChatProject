package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.zwj.aop.Nullable;
import com.zwj.entity.User;
import com.zwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("loginController")
@Controller    //LoginController加入Ioc容器
@SessionAttributes(value={"loginUser"})
public class LoginController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("user")
    private User loginUser;

    @Autowired
    @Qualifier("user")
    private User user;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param userJson POST方式自动封装成的json字符串
     */
    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String login(@RequestBody @Nullable String userJson, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) {

        user = JSON.parseObject(userJson,User.class);

        return userService.login(user.getUser_name(),user.getUser_password());
        //return "index";
    }

}
