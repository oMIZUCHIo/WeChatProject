package com.zwj.entity;

import org.springframework.stereotype.Component;

import java.util.List;

//用户展示群聊基本信息及群中用户的基本信息
@Component
public class UserGroupVo {

    private UserGroups userGroups;  //群信息
    private List<UserVo> userVoList;//群中用户的基本信息集合

    public UserGroupVo(UserGroups userGroups, List<UserVo> userVoList) {
        this.userGroups = userGroups;
        this.userVoList = userVoList;
    }

    public UserGroupVo() {
    }

    public void setUserGroups(UserGroups userGroups) {
        this.userGroups = userGroups;
    }

    public void setUserVoList(List<UserVo> userVoList) {
        this.userVoList = userVoList;
    }

    public UserGroups getUserGroups() {
        return userGroups;
    }

    public List<UserVo> getUserVoList() {
        return userVoList;
    }

}
