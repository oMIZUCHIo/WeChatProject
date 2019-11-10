package com.zwj.webSocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

//获取httpSession
public class HttpSessionConfig extends ServerEndpointConfig.Configurator{

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession session = (HttpSession) request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(), session);
    }
}
