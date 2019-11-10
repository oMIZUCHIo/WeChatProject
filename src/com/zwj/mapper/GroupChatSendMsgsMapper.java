package com.zwj.mapper;

import com.zwj.entity.GroupChatSendMsgs;

import java.util.List;

public interface GroupChatSendMsgsMapper {

    //新增群聊发送信息
    int addGroupSendMsg(GroupChatSendMsgs groupChatSendMsgs);

    //根据ID获得群聊发送信息
    List<GroupChatSendMsgs> getGroupChatSendMsg(List<Integer> sendMsgIds);

    //删除群聊中的所有发送信息
    int deleteGroupSendMsg(int groupId);
}
