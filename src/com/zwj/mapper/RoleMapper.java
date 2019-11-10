package com.zwj.mapper;

import com.zwj.entity.UserAndRole;

//@Repository(value = "roleMapper")
public interface RoleMapper {

    //新建用户与角色间的关联
    int addUserAndRole(UserAndRole userAndRole);
}
