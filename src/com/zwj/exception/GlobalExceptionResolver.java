package com.zwj.exception;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Component
@ControllerAdvice        //指定该类为 Controller 增强类。
public class GlobalExceptionResolver {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionResolver.class);

    private MyRuntimeException myRuntimeException;

    @ExceptionHandler(Exception.class)  //自定捕获的异常类型
    @ResponseBody
    public String handleException(HttpServletRequest request, Exception ex){

        JSONObject jsonObject = new JSONObject();
        try {

            if(ex instanceof MyRuntimeException){

                myRuntimeException = (MyRuntimeException)ex;
                jsonObject.put("code",myRuntimeException.getCode());
                jsonObject.put("description",myRuntimeException.getDescription());
                logger.error("出现自定义错误！***********" + myRuntimeException.getDescription());

            }else{
                jsonObject.put("code","1000");
                jsonObject.put("description", ex.getMessage());

                logger.error("出现错误！***********" + ex.getMessage());
            }

        } catch (Exception e) {
          logger.error("CatchExceptionHandler 异常处理失败", e);
        }
        return jsonObject.toJSONString();
    }

}
