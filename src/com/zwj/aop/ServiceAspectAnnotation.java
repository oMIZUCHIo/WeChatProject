
package com.zwj.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component("serviceAspectAnnotation")
@Aspect  //声明该类 是一个 通知
public class ServiceAspectAnnotation {

    private static final Logger logger = LogManager.getLogger(ServiceAspectAnnotation.class);

    private String logStr=null;

@Around("execution(* com.zwj.controller.*.*(..))")
public Object aroundMethod(ProceedingJoinPoint pjd){

    Object result = null;
    String methodName = pjd.getSignature().getName();
    String className  = pjd.getTarget().getClass().getName();

    try {
        //前置通知
        logStr = "Service层的" + className + "类的" + methodName + "方法开始执行******Start******";
        logger.info(logStr);

        //执行目标方法
        result = pjd.proceed();

        //后置通知
        logStr = "Service层的" + className + "." + methodName + "()方法正常执行结束...";
        logger.info(logStr);

    } catch (Throwable e) {
        //异常通知
        StackTraceElement stackTraceElement= e.getStackTrace()[e.getStackTrace().length-1];
        logger.error("----------------------------------------------------------------------------------");
        logger.error ( "Service层的" + className + "类的" + methodName + "异常信息为：",e);

        throw new RuntimeException(e);
    }finally {
        //相当于最终通知
        logStr = "Service层的" + className+"类的" +methodName+"方法执行结束=======End=======";
        logger.info(logStr);
    }

    return result;
}

}

