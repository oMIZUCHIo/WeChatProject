package com.zwj.service.impl;

import com.zwj.entity.Content;
import com.zwj.entity.Messages;
import com.zwj.entity.MessagesVo;
import com.zwj.entity.User;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.ContentMapper;
import com.zwj.mapper.MessagesMapper;
import com.zwj.mapper.UserMapper;
import com.zwj.service.MessagesService;
import com.zwj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "messagesService")
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    @Qualifier("messagesMapper")
    private MessagesMapper messagesMapper;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("contentMapper")
    private ContentMapper contentMapper;

    @Autowired
    @Qualifier("messages")
    private Messages messages;

    @Autowired
    @Qualifier("content")
    private Content contents;

    @Autowired
    private List<Messages> messagesList;

    @Autowired
    private List<MessagesVo> messagesVoList;

    private List<Integer> messageIdList = new ArrayList<>();

    @Autowired
    @Qualifier("user")
    private User user;

    @Autowired
    @Qualifier("messagesVo")
    private MessagesVo messagesVo;

    //@Autowired
    private Map<String,Integer> map ;


    public void setMessagesMapper(MessagesMapper messagesMapper) {
        this.messagesMapper = messagesMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setContentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public void setContents(Content contents) {
        this.contents = contents;
    }

    public void setMessagesVo(MessagesVo messagesVo) {
        this.messagesVo = messagesVo;
    }

    //获得用户未接收到的单聊信息集合
    @Override
    public List<MessagesVo> queryNoReceiveMsg(int userId) {

        List<Integer> fromUserIdList = messagesMapper.queryNoReceiveFromUserId(userId);

        map = new HashMap<>();
        map.put("to_user_id",userId);

        messagesList.clear();
        messagesVoList.clear();

        for(int fromUserId : fromUserIdList){
            map.put("from_user_id",fromUserId);

            messagesList = messagesMapper.queryNoReceiveMsg(map);

            user = userMapper.queryUserByUserId(fromUserId);

            //帮助前端逐条渲染信息
            for(Messages messages : messagesList){
                messages.setFrom_user_name(user.getUser_name());
                messages.setFrom_user_url(user.getUser_icon());
            }

            messagesVo = new MessagesVo();

            messagesVo.setFrom_user_id(user.getUser_id());
            messagesVo.setFrom_user_name(user.getUser_name());
            messagesVo.setFrom_user_url(user.getUser_icon());
            messagesVo.setFrom_user_status(user.getUser_status());
            messagesVo.setNo_receive_num(messagesList.size());
            messagesVo.setStick_to_top(0);

            //设置最后一条信息
            if(messagesList.size() != 0){

                messagesVo.setLast_content(messagesList.get(messagesList.size()-1).getContent());

                messagesVo.setMessagesList(messagesList);
            }

            messagesVoList.add(messagesVo);
        }

        return messagesVoList;
    }

    //新增单聊记录
    @Override
    public String addMessage(Messages messages) {

        contents.setContent(messages.getContent());
        contents.setContent_type(messages.getContent_type());
        contents.setFile_name(messages.getFile_name());
        contents.setFile_size(messages.getFile_size());

        //先新增聊天记录
        if(contentMapper.addContentReturnId(contents) <= 0)
            throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);

        messages.setContent_id(contents.getContent_id());

        //判断对方是否是自己的好友
        if(messagesMapper.addMessages(messages) > 0 ){
            //对方不是好友则拒绝消息，抛出错误
            if(messages.getStatus() == 3){
                throw new MyRuntimeException(ErrorCodeEnum.ISNOT_FRIEND);
            }else{
                throw new MyRuntimeException(ErrorCodeEnum.SUCCESS);
            }
        }else{
            throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
        }
    }

    //将单聊未读信息改为已读
    @Override
    public String messageToHaveRead(int userId,int friendId) {

        map.clear();

        map.put("from_user_id",friendId);
        map.put("to_user_id",userId);
        map.put("status",2);

        //获得两用户间所有未读信息ID集合
        messageIdList = messagesMapper.queryStatusMsgId(map);

        if(messageIdList.size() == 0){
            throw new MyRuntimeException(ErrorCodeEnum.SUCCESS);
        }else{
            return StringUtil.returnDefaultResult(messagesMapper.updateMessageType(messageIdList) > 0);
        }
    }
}
