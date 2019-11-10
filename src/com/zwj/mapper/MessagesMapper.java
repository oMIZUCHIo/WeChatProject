package com.zwj.mapper;

import com.zwj.entity.Messages;

import java.util.List;
import java.util.Map;

//@Repository(value = "messagesMapper")
public interface MessagesMapper {

    //获得未接收信息集合
    List<Messages> queryNoReceiveMsg(Map<String,Integer> map);

    //获取用户未接收信息来源用户ID集合
    List<Integer> queryNoReceiveFromUserId(int userId);

    //新增聊天记录
    int addMessages(Messages messages);

    //更新信息状态
    int updateMessageType(List<Integer> list);

    //获得特定状态下的信息ID集合
    List<Integer> queryStatusMsgId(Map<String,Integer> map);

}
