package com.zwj.controller;

import com.zwj.service.GroupChatReceiveMsgsService;
import com.zwj.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("upDateMessageTypeController")
@Controller
public class UpDateMessageTypeController {

    @Autowired
    @Qualifier("messagesService")
    private MessagesService messagesService;

    @Autowired
    @Qualifier("groupChatReceiveMsgsService")
    private GroupChatReceiveMsgsService groupChatReceiveMsgsService;

    public void setMessagesService(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public void setGroupChatReceiveMsgsService(GroupChatReceiveMsgsService groupChatReceiveMsgsService) {
        this.groupChatReceiveMsgsService = groupChatReceiveMsgsService;
    }

    /**
     * 将单聊未读消息设为已读
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    @ResponseBody
    @RequestMapping(value = "updateMessageType",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateMesageType(@RequestParam int userId , @RequestParam int friendId, HttpServletResponse response, Map<String,Object> map){

        return messagesService.messageToHaveRead(userId,friendId);
    }


    /**
     * 将群聊未读消息设为已读
     * @param userId 用户ID
     * @param groupId 群ID
     */
    @ResponseBody
    @RequestMapping(value = "updateGroupMessageType",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String updateGroupMessageType(@RequestParam int userId , @RequestParam int groupId, HttpServletResponse response, Map<String,Object> map){

        return groupChatReceiveMsgsService.groupMsgToHaveRead(userId, groupId);
    }

}
