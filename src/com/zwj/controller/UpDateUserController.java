package com.zwj.controller;

import com.zwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("upDateUserController")
@Controller
public class UpDateUserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户更改昵称
     * @param userId 用户ID
     * @param nickName 昵称名
     */
    @ResponseBody
    @RequestMapping(value = "updateNickName",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateNickName(@RequestParam int userId,@RequestParam String nickName, HttpServletResponse response, Map<String,Object> map){

        return userService.updateNickName(userId,nickName);
    }


    /**
     * 用户更改性别
     * @param userId 用户ID
     * @param sex 性别
     */
    @ResponseBody
    @RequestMapping(value = "updateSex",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateSex(@RequestParam int userId,@RequestParam String sex, HttpServletResponse response, Map<String,Object> map){

        return userService.updateSex(userId,sex);
    }

    /**
     * 用户更改生日
     * @param userId 用户ID
     * @param birthday 生日
     */
    @ResponseBody
    @RequestMapping(value = "updateBirthday",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateBirthday(@RequestParam int userId,@RequestParam String birthday, HttpServletResponse response, Map<String,Object> map){

        return userService.updateBirthday(userId,birthday);
    }


    /**
     * 用户更改个性签名
     * @param userId 用户ID
     * @param personSignature 个性签名
     */
    @ResponseBody
    @RequestMapping(value = "updatePersonSignature",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updatePersonSignature(@RequestParam int userId,@RequestParam String personSignature, HttpServletResponse response, Map<String,Object> map){

        return userService.updatePersonSignature(userId,personSignature);
    }
}
