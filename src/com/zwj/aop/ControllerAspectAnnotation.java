package com.zwj.aop;

import com.sun.istack.internal.Nullable;
import com.zwj.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component("controllerAspectAnnotation")
@Aspect  //声明该类 是一个 通知
public class ControllerAspectAnnotation {

    private static final Logger logger = LogManager.getLogger(ControllerAspectAnnotation.class);

    private String logStr=null;

    @Around("execution(* com.zwj.controller.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjd){

        Object result = null;
        String methodName = pjd.getSignature().getName();
        String className  = pjd.getTarget().getClass().getName();

        try {
            //前置通知
        //    System.out.println("《Controller层前置通知》：目标对象："+pjd.getTarget()+",方法名："+pjd.getSignature().getName() +",参数列表："+ pjd.getArgs().length  );

            logStr = "Controller层的" + className + "类的" + methodName + "方法开始执行******Start******";
            logger.info(logStr);

            for(Object object : pjd.getArgs()){

                if(object instanceof HttpServletResponse){

                    HttpServletResponse response = (HttpServletResponse)object;

                    response.setHeader("Cache-Control", "no-store");

                    //解决跨域
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    // 设置响应头允许ajax跨域访问
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    // 星号表示所有的异域请求都可以接受
                    response.setHeader("Access-Control-Allow-Methods","GET,POST");

                    response.setHeader("Content-Type", "application/json;charset=UTF-8");

                    response.setHeader("Header","*");

                }
            }
            MethodSignature signature = (MethodSignature) pjd.getSignature();
            // 获得切入方法参数
            Object[] args = pjd.getArgs();
            // 获得切入的方法
            Method method = signature.getMethod();
            // 获得所有参数
            Parameter[] parameters = method.getParameters();
            // 获得所有参数名
            String[] parameterNames = signature.getParameterNames();
            // 保存需要校验的args
            // ArrayList<Object> argsWithoutNullable = new ArrayList<>();
            // 对没有Nullable注解的参数进行非空校验
            for (int i = 0; i < parameters.length; i++) {

                Parameter parameter = parameters[i];

                Annotation[] annotations = parameter.getDeclaredAnnotationsByType(Nullable.class);
                if (annotations.length < 1) {
                    StringUtil.ifNull(args[i],parameterNames[i]);
                }
            }

            //执行目标方法
            result = pjd.proceed();

            //后置通知
            logStr = "Controller层的" + className + "." + methodName + "()方法正常执行结束...";
            logger.info(logStr);

        } catch (Throwable e) {
            //异常通知
            StackTraceElement stackTraceElement= e.getStackTrace()[e.getStackTrace().length-1];
            logger.error("----------------------------------------------------------------------------------");
            logger.error ( "Controller层的" + className + "类的" + methodName + "异常信息为：",e);


            throw new RuntimeException(e);
        }finally {
            //相当于最终通知
            logStr = "Controller层的" + className+"类的" +methodName+"方法执行结束=======End=======";
            logger.info(logStr);
        }

        return result;
    }
}

