package com.zwj.service;

import com.zwj.entity.GroupChatNoReceiveMsgs;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

public interface GroupChatReceiveMsgsService {

    //获得某用户在所有群聊中未接收到的所有群聊信息
    List<GroupChatNoReceiveMsgs> getNoReceiveGroupMsg(int userId);

    //给群聊中的用户新增接收信息
    String addGroupReceiveMsg(int gcsmId, int groupId, List<Integer> userIdsList, Map<Integer, WebSocketSession> loginUserMap);

    //将未读信息改为已读
    String groupMsgToHaveRead(int userId,int groupId);

}
