package com.zwj.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {

    //根据contentType获取文件类型
    public static String getTypeName(int contentType){

        switch (contentType){

            case 2 : return "png";
            case 3 : return "jpg";
            case 4 : return "jpeg";
            case 5 : return "gif";
            case 6 : return "mp3";
            case 7 : return "doc";
            case 8 : return "docx";
            case 9 : return "xls";
            case 10 : return "xlsx";
            case 11 : return "ppt";
            case 12 : return "pptx";
            case 13 : return "pdf";

            default : return "error";
        }
    }

    // 将 file 转化为 Base64
    public static String fileToBase64(String path) {
        File file = new File(path);
        FileInputStream inputFile;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            throw new RuntimeException("文件路径无效：" + e.getMessage());
        }
    }

    // 将 base64 转化为 file并上传到相应的位置
    public static boolean base64ToFile(String base64, String path) {
        byte[] buffer;
        try {
            buffer = new BASE64Decoder().decodeBuffer(base64);
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("base64字符串异常或地址异常：" + e.getMessage());
        }
    }


    //获得项目在Tomcat上的资源路径
    public static String getResProjectPath(){
        //获取user.dir在Tomcat服务器所在的bin路径
        String tomcat_path = System.getProperty("user.dir");
        //获取Tomcat服务器目录
        return tomcat_path.substring(0, tomcat_path.lastIndexOf("\\"));
    }
}
