package com.zwj.util;

import com.alibaba.fastjson.JSONObject;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;

public class StringUtil {

    //数据库操作成功就返回默认结果
    public static String returnDefaultResult(boolean flag) {

        if (flag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("description", "操作成功");
            System.out.println("jsonObject.toJSONString()" + jsonObject.toJSONString());
            return jsonObject.toJSONString();
        } else {
            throw new MyRuntimeException(ErrorCodeEnum.DATABASE_ERROR);
        }
    }

    //判断参数是否为空
    public static void ifNull(Object object, String paramName) {

        System.out.println("object" + object + "，paramName" + paramName);

        if (object == null) {
            throw new MyRuntimeException("1002", "参数" + paramName + "为空");
        }

        if (object instanceof Integer) {
            if (0 == (int) object) {
                throw new MyRuntimeException("1002", "整型参数：" + paramName + "为空");
            }
        }
        if (object instanceof String) {
            if ("".equals((String) object)) {
                throw new MyRuntimeException("1002", "字符串参数：" + paramName + "为空");
            }

        }
    }

    //转义前端传入的数据
    public static String string2Json(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); i++) {

            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case ':':
                    sb.append("\\:");
                    break;
                case ',':
                    sb.append("\\,");
                    break;
                case '{':
                    sb.append("\\{");
                    break;
                case '}':
                    sb.append("\\}");
                    break;
                case '[':
                    sb.append("\\[");
                    break;
                case ']':
                    sb.append("\\]");
                    break;
                default:
                    sb.append(c);
            }
            }
                return sb.toString();
        }



}
