package com.zwj.mapper;

import com.zwj.entity.Content;

//@Repository(value = "contentMapper")
public interface ContentMapper {

    //根据ID获取信息
    Content queryContentById(int contentId);

    //新增信息并返回ID
    int addContentReturnId(Content content);

    //删除信息
    int deleteContent(int groupId);
}
