package com.zwj.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
@Configuration的作用
声明当前类是一个Spring配置类
@Configuration不可以是final类型；
@Configuration不可以是匿名类；
嵌套的configuration必须是静态类。
@EnableWebSocket  声明该类支持WebSocket
 */
@Configuration
@EnableWebSocket
@EnableWebMvc
public class SocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //setAllowedOrigins方法用来设置来自那些域名的请求可访问
        registry.addHandler(WebSocketPushHandler(), "/ChatWebSocket").addInterceptors(new MyWebSocketInterceptor()).setAllowedOrigins("*");
        //允许客户端使用SockJS
        registry.addHandler(WebSocketPushHandler(), "/sockjs/ChatWebSocket")
                .addInterceptors(new MyWebSocketInterceptor()).withSockJS();
    }

    @Bean
    public WebSocketHandler WebSocketPushHandler() {
        return new ChatWebSocket();
    }

}
