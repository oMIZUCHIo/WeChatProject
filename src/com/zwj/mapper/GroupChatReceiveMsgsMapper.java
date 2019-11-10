package com.zwj.mapper;

import com.zwj.entity.GroupChatReceiveMsgs;

import java.util.List;
import java.util.Map;

public interface GroupChatReceiveMsgsMapper {

    //新增群聊接收信息
    int addGroupReceiveMsg(GroupChatReceiveMsgs groupChatReceiveMsgs);

    //获得群聊接收信息对应的发送信息ID集合
    List<Integer> getGroupChatSendMsgsIds(GroupChatReceiveMsgs groupChatReceiveMsgs);

    //删除群聊中的接收信息
    int deleteGroupReceiveMsg(int groupId);

    //更改群聊接收信息类型
    int updateGroupMessageType(List<Integer> list);

    //获得群聊特定状态下的接收信息ID集合
    List<Integer> queryStatusGroupMsgId(Map<String,Integer> map);
}
