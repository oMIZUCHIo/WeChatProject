package com.zwj.util;

import com.alibaba.fastjson.JSONObject;
import com.zwj.exception.MyRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    //签名私钥
    private final static String base64EncodedSecretKey = "secrectKey";

    //过期时间,一小时
    private final static long TOKEN_EXP = 1000 * 60 * 60;

    public static String creatToken(int userId) throws Exception {

        // 指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();// 生成JWT的时间

        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id",userId);

        // 为payload添加各种标准声明和私有声明了
        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setClaims(payload)   // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(userId + "") // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(new Date(nowMillis)) //  jwt的签发时间
                .setSubject(userId + "") //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid等，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, base64EncodedSecretKey);// 设置签名使用的签名算法和签名使用的秘钥
               // .setExpiration(new Date(nowMillis + TOKEN_EXP));// 设置过期时间

        return builder.compact(); // 就开始压缩为jwt
    }

    /**
     * 解密jwt
     */
    public static Claims checkToken(String jwt) throws Exception {

        JSONObject jsonObject = new JSONObject();

        try {
            Claims claims = Jwts.parser()                   // 得到DefaultJwtParser
                    .setSigningKey(base64EncodedSecretKey)  // 设置签名的秘钥
                    .parseClaimsJws(jwt).getBody();         // 设置需要解析的jwt
            return claims;
        }catch (Exception e){
            throw new MyRuntimeException("token解析错误");
        }
    }
}
