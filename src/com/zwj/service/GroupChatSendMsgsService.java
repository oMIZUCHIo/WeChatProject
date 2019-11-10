package com.zwj.service;

public interface GroupChatSendMsgsService {

    //新增群聊发送信息
    int addGroupSendMsg(int fromUserId, int ugId, String content, int contentType,String fileName,String fileSize);
}
