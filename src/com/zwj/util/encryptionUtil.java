package com.zwj.util;

import org.apache.commons.codec.digest.DigestUtils;

//采用MD5进行对密码的加密
public class encryptionUtil {

    public static String md5Encode(byte[ ] input){ //byte[ ] ("abc") --> String("xxxxx")
        return DigestUtils.md5Hex(input);  //byte[ ] --> String
    }

    public static String md5Encode(String input){ //byte[ ] ("abc") --> String("xxxxx")
        return DigestUtils.md5Hex(input.getBytes());  //byte[ ] --> String
    }
}
