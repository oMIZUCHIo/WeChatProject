package com.zwj.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        String token = httpServletRequest.getHeader("Token");
        System.out.println(token);

        try {
            if (token == null) {
                throw new RuntimeException("无token，请重新登录");
            } else {

             /*   Claims claims = JWTUtil.checkToken(token);

                if(claims == null){
                    jsonObject.put("state","token解析错误，验证失败");
                }else{
                    jsonObject.put("state","token解析成功");
                    jsonObject.put("user_id",claims.getSubject());
                }
                httpServletRequest.setAttribute("result",jsonObject.toJSONString());
*/
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token出错！");
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
