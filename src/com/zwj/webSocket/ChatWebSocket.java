package com.zwj.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zwj.entity.*;
import com.zwj.service.*;
import com.zwj.util.DateUtil;
import com.zwj.util.FileUtil;
import com.zwj.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatWebSocket  extends TextWebSocketHandler {

    private static final Logger logger = LogManager.getLogger(ChatWebSocket.class);

    private static Map<Integer, WebSocketSession> loginUserMap = new HashMap<>();

    private static int userId;

    private static ApplicationContext act = ApplicationContextRegister.getApplicationContext();

    private static UserService userService = act.getBean(UserService.class);
    private static FriendsService friendsService = act.getBean(FriendsService.class);
    private static MessagesService messagesService = act.getBean(MessagesService.class);
    private static GroupChatReceiveMsgsService groupChatReceiveMsgsService = act.getBean(GroupChatReceiveMsgsService.class);
    private static GroupChatSendMsgsService groupChatSendMsgsService = act.getBean(GroupChatSendMsgsService.class);
    private static UsersOfUserGroupService usersOfUserGroupService = act.getBean(UsersOfUserGroupService.class);

    private static User user = act.getBean(User.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        System.out.println("OnOpen");

        //解析token获得的登录用户ID
        userId = Integer.valueOf((String) webSocketSession.getAttributes().get("userId"));

        logger.info("===========socket正常打开，连接用户ID：" + userId);

        //将当前登录用户加入WebSocketSession集合，此时用户在线
        loginUserMap.put(userId, webSocketSession);

        //将当前用户在数据库中的状态置为1（即用户在线状态）
        boolean success = userService.loginSystem(userId);

        if (!success) {
            sendMessage(webSocketSession,"系统出错！");
        }else{
            user = userService.queryUserByUserId(userId);
            //检查自己是否有未接收的单聊消息
            JSONObject jsonObject = new JSONObject();

            //检查自己是否有未读单聊信息
            List<MessagesVo> noReceivedMessages = messagesService.queryNoReceiveMsg(userId);
            jsonObject.put("noReceivedMessages",noReceivedMessages);

            sendMessage(webSocketSession,jsonObject.toJSONString());

            jsonObject = new JSONObject();
            //检查自己是否有未接收的群聊消息
            List<GroupChatNoReceiveMsgs> groupChatNoReceiveMsgs = groupChatReceiveMsgsService.getNoReceiveGroupMsg(userId);
            jsonObject.put("groupChatNoReceiveMsgs",groupChatNoReceiveMsgs);
            sendMessage(webSocketSession, JSON.toJSONString(jsonObject,SerializerFeature.DisableCircularReferenceDetect));
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {

        System.out.println("textMessage：" + message.getPayload());

        logger.info("===============socket接收消息：" + message.getPayload());

        //心跳测试（未完善）
        if("ping".equals(message.getPayload())){

            System.out.println("pong");
            sendMessage(webSocketSession,"pong");
        }else{

            JSONObject jsonObject = JSON.parseObject(message.getPayload());

            int messageType = Integer.valueOf(jsonObject.get("messageType").toString());

            String content = null;

            int contentType = Integer.valueOf(jsonObject.get("contentType").toString());

            //messageType为1时表名此时是单聊
            if(messageType == 1){

                Messages toMessage = new Messages();

                int toUserId = Integer.valueOf( jsonObject.get("toUserId").toString());

                if(contentType > 1){

                    //contentType >= 6表明为非图片文件
                    if(contentType >= 6){

                        String fileName = jsonObject.get("fileName").toString();
                        toMessage.setFile_name(fileName);

                        String fileSize = jsonObject.get("fileSize").toString();
                        toMessage.setFile_size(fileSize);
                    }

                    //前端传入错误的content_type
                    if("error".equals(FileUtil.getTypeName(contentType))){
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("result","请传入正确的文件类型");
                        sendMessage(webSocketSession,jsonObject1.toJSONString());
                    }else{

                        JSONObject jsonObject01 = new JSONObject();

                        String resProjectPath = FileUtil.getResProjectPath();

                        String fileType = FileUtil.getTypeName(contentType);

                        content = "\\WeChatProjectResourse\\UserFile\\" + userId + "\\" + System.currentTimeMillis() + "." + fileType;

                        String filePath = resProjectPath + content;

                        //解析base64上传
                        if(FileUtil.base64ToFile(jsonObject.get("file").toString(),filePath)){

                            jsonObject01.put("fileUpLoadResult","上传成功");
                        }else{
                            jsonObject01.put("fileUpLoadResult","上传失败");
                        }
                        sendMessage(webSocketSession,jsonObject01.toJSONString());
                    }

                }else{
                    //对用户传入的内容进行转义
                    content = jsonObject.get("content").toString();
                    content = StringUtil.string2Json(content);
                }

                toMessage.setFrom_user_id(userId);
                toMessage.setTo_user_id(toUserId);
                toMessage.setFrom_user_name(user.getUser_name());
                toMessage.setFrom_user_url(user.getUser_icon());
                toMessage.setContent(content);
                toMessage.setContent_type(contentType);
                toMessage.setSend_time(DateUtil.getNowTime());

                sendUserMessage(userId,toUserId,toMessage);

                //messageType为2时表名此时是群聊
            }else if(messageType == 2){

                GroupChatReceiveMsgsVo groupChatReceiveMsgsVo = new GroupChatReceiveMsgsVo();

                int toGroupId = Integer.valueOf(jsonObject.get("toGroupId").toString());

                if(contentType > 1){

                    if(contentType >= 6){

                        String fileName = jsonObject.get("fileName").toString();
                        groupChatReceiveMsgsVo.setFile_name(fileName);

                        String fileSize = jsonObject.get("fileSize").toString();
                        groupChatReceiveMsgsVo.setFile_size(fileSize);
                    }

                    if("error".equals(FileUtil.getTypeName(contentType))){
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("result","请传入正确的文件类型");
                        sendMessage(webSocketSession,jsonObject1.toJSONString());
                    }else{

                        JSONObject jsonObject01 = new JSONObject();

                        //获得项目在Tomcat上的资源路径
                        String resProjectPath = FileUtil.getResProjectPath();

                        String fileType = FileUtil.getTypeName(contentType);

                        content = "\\WeChatProjectResourse\\UserGroupFile\\" + toGroupId + "\\" + System.currentTimeMillis() + "." + fileType;

                        String filePath = resProjectPath + content;

                        if(FileUtil.base64ToFile(jsonObject.get("file").toString(),filePath)){

                            jsonObject01.put("fileUpLoadResult","上传成功");
                        }else{
                            jsonObject01.put("fileUpLoadResult","上传失败");
                        }
                        sendMessage(webSocketSession,jsonObject01.toJSONString());
                    }

                }else{
                    //对用户传入的内容进行转义
                    content = jsonObject.get("content").toString();
                    content = StringUtil.string2Json(content);
                }

                groupChatReceiveMsgsVo.setUg_id(toGroupId);
                groupChatReceiveMsgsVo.setFrom_user_id(userId);
                groupChatReceiveMsgsVo.setFrom_user_icon(user.getUser_icon());
                groupChatReceiveMsgsVo.setContent(content);
                groupChatReceiveMsgsVo.setContent_type(contentType);
                groupChatReceiveMsgsVo.setSend_time(DateUtil.getNowTime());

                String nickName = usersOfUserGroupService.queryNickNameInUserGroup(userId,toGroupId);

                if(nickName != null){

                    groupChatReceiveMsgsVo.setFrom_user_nick_name(nickName);

                }else if(user.getUser_nick_name() != null){

                    groupChatReceiveMsgsVo.setFrom_user_nick_name(user.getUser_nick_name());
                }else{

                    groupChatReceiveMsgsVo.setFrom_user_nick_name(user.getUser_name());
                }
                sendUserGroupMessage(userId,usersOfUserGroupService.queryUserIdsInGroup(toGroupId),groupChatReceiveMsgsVo);
            }
        }

    }


    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("OnError");
        logger.info("=============socket异常关闭：" + throwable.getMessage());
        System.out.println(throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("OnClose");

        logger.info("==============socket关闭：");

        //将当前登录用户从Socket集合中移除
        loginUserMap.remove(userId);

        //将用户在线状态置2（即用户离线状态）
        boolean success = userService.existSystem(userId);
        if(!success) {
            sendMessage(webSocketSession,"系统出错！");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    //向目标WebSocketSession发送信息
    private void sendMessage(WebSocketSession webSocketSession, String message){

        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向单个用户发送信息
    private void sendUserMessage(int loginUserId,int toUserId, Messages toMessage){

        //获得对方的WebSocketSession
        WebSocketSession toUserSession = loginUserMap.get(toUserId);

        JSONObject toMsgJsonObject = new JSONObject();

        //判断两用户是否为好友
        if(! friendsService.isFriends(loginUserId,toUserId)){

            toMessage.setStatus(3);

        }else{

            if(toUserSession != null){

                //当单聊对象在线时直接发送
                toMsgJsonObject.put("messageType",1);

                toMessage.setStatus(1);
                toMsgJsonObject.put("toMessage",toMessage);

                System.out.println("toUserSession" + toMsgJsonObject.toJSONString());
                sendMessage(toUserSession,toMsgJsonObject.toJSONString());

                //数据库存储信息（需完善）
            }else{

                //（目前简单处理，后续可根据传来的页面标识进行判断处理）
                toMessage.setStatus(2);
                //当单聊对象不在线时将此信息存入数据库（信息状态为未读）
            }
        }
        String msg = messagesService.addMessage(toMessage);
        sendMessage(loginUserMap.get(loginUserId),msg);
    }

    //向群聊中的用户发送信息
    private void sendUserGroupMessage(int loginUserId,List<Integer> userIds, GroupChatReceiveMsgsVo groupChatReceiveMsgsVo){

        //数据库存储该用户发送的信息记录
        int gcsmId = groupChatSendMsgsService.addGroupSendMsg(loginUserId,groupChatReceiveMsgsVo.getUg_id(),groupChatReceiveMsgsVo.getContent(),groupChatReceiveMsgsVo.getContent_type(),groupChatReceiveMsgsVo.getFile_name(),groupChatReceiveMsgsVo.getFile_size());

        JSONObject toMsgJsonObject = new JSONObject();

        if(gcsmId == -1){

            toMsgJsonObject.put("result","系统出错！");
            sendMessage(loginUserMap.get(loginUserId),toMsgJsonObject.toJSONString());

        }else{
            //数据库存储群聊中用户接收的信息记录
            sendMessage(loginUserMap.get(loginUserId),groupChatReceiveMsgsService.addGroupReceiveMsg(gcsmId,groupChatReceiveMsgsVo.getUg_id(),userIds,loginUserMap));

            //对群聊中的用户发送信息
            for(Integer userId : userIds) {

                WebSocketSession toUserSession = loginUserMap.get(userId);

                toMsgJsonObject.put("messageType",2);

                toMsgJsonObject.put("groupChatReceiveMsgsVo",groupChatReceiveMsgsVo);

                if (toUserSession != null) {

                    sendMessage(toUserSession,toMsgJsonObject.toJSONString());
                }
            }
        }
    }

}

