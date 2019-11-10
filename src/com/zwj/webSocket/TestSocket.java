package com.zwj.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwj.util.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class TestSocket extends TextWebSocketHandler {

    FileOutputStream output;
    FileInputStream input;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        System.out.println("afterConnectionEstablished");
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession,TextMessage message) throws Exception {
        //handleMessageTextMessage payload=[小程序端测试], byteCount=18, last=true]
        System.out.println("handleMessage：" + message);
        System.out.println("textMessage：" + message.getPayload());

        JSONObject jsonObject = JSON.parseObject(message.getPayload());
        System.out.println("数据：" + jsonObject.get("msg").toString());
        try {
            String filePath = "D:\\tomcat-9.0.19\\" + System.currentTimeMillis() + ".jpg";
            if(FileUtil.base64ToFile(jsonObject.get("file").toString(),filePath)){
                webSocketSession.sendMessage(new TextMessage("上传成功"));
            }else{
                webSocketSession.sendMessage(new TextMessage("上传失败"));
            }

          //  System.out.println(payload.size());
          //  payload.writeTo(output);
         //   System.out.println("img" + img.length() + "," + img.getName());
         //   input = new FileInputStream(img);
         //   int content = input.read();
          //  while(content!=-1){
          //      output.write(content);
          //      content = input.read();
         //   }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        webSocketSession.sendMessage(new TextMessage("这个是后台传来的数据"));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("handleTransportError");

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("afterConnectionClosed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void sendPicture(WebSocketSession session, String fileName){
        try {
            File file=new File("D:\\tomcat-9.0.19\\"+fileName);
            input = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            input.read(bytes);
            BinaryMessage byteMessage=new BinaryMessage(bytes);
            session.sendMessage(byteMessage);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
