package com.zwj.service;

import com.zwj.entity.Messages;
import com.zwj.entity.MessagesVo;

import java.util.List;

public interface MessagesService {

    //获得用户未接收到的单聊信息集合
    List<MessagesVo> queryNoReceiveMsg(int userId);

    //新增单聊记录
    String addMessage(Messages messages);

    //将单聊未读信息改为已读
    String messageToHaveRead(int userId,int friendId);
}
