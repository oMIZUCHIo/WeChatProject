package com.zwj.webSocket;

import com.zwj.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
握手拦截器类
 */
public class MyWebSocketInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LogManager.getLogger(ChatWebSocket.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
                                   Map<String, Object> attributes) throws Exception {
        // 将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息
        if (response instanceof ServletServerHttpResponse) {

            ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) response;
            HttpServletResponse httpResponse = servletResponse.getServletResponse();
            //解决跨域
            httpResponse.setContentType("application/json");
            // 设置响应头允许ajax跨域访问
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            // 星号表示所有的异域请求都可以接受
            httpResponse.setHeader("Access-Control-Allow-Methods","*");

            httpResponse.setHeader("Header","*");
        }

        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest)request;
        HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();

        String token = httpServletRequest.getHeader("Token");
        System.out.println("MyWebSocketInterceptor Token"+token);
        try {
            if (token == null) {
                logger.error("--------------------连接socket时无token");
                throw new RuntimeException("无token，请重新登录");
            } else {

                Claims claims = JWTUtil.checkToken(token);

                if(claims == null){
                    logger.error("--------------------连接socket时token解析错误，验证失败");
                    throw new RuntimeException("token解析错误，验证失败");
                }else{

                    attributes.put("userId",claims.getSubject());
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("--------------------握手成功--------");

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
    }

}
