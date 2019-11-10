package com.zwj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwj.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("TestController")
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping(value = "test01",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String test01(@RequestBody String contentJson, HttpServletResponse response){
        // 'content-type': 'application/x-www-form-urlencoded'
        //解决跨域
        response.setContentType("application/json");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        response.setHeader("Access-Control-Allow-Methods","GET,POST");

        JSONObject jsonObject = JSON.parseObject(contentJson);
        System.out.println("test01输入值为：" + (String)jsonObject.get("content") + "，" + (String)jsonObject.get("msg"));
        return "这个是输出值";
        // return "这个是输出值";
    }

    @ResponseBody
    @RequestMapping(value = "test02",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String test02(@RequestBody String contentJson, HttpServletResponse response, Map<String,Object> map){
        // 'content-type': 'application/x-www-form-urlencoded'
        //解决跨域
        response.setContentType("application/x-www-form-urlencoded");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        response.setHeader("Access-Control-Allow-Methods","GET,POST");

        JSONObject jsonObject = JSON.parseObject(contentJson);
        System.out.println("test01输入值为：" + (String)jsonObject.get("content") + "，" + (String)jsonObject.get("msg"));
        return "这个是输出值";
    }


    @ResponseBody
    @RequestMapping(value = "test03",method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public String test03(@RequestParam String content,@RequestParam String msg, HttpServletResponse response,Map<String,Object> map){
        // 'content-type': 'application/x-www-form-urlencoded'
        //解决跨域
        response.setContentType("application/json");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        response.setHeader("Access-Control-Allow-Methods","GET,POST");

        System.out.println("test03输入值为：" + content + "，" + msg);
        return "这个是输出值";
    }


    @ResponseBody
    @RequestMapping(value = "test04",method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public String test04(@RequestParam String contentJson, HttpServletResponse response, Map<String,Object> map){
        // 'content-type': 'application/x-www-form-urlencoded'
        //解决跨域
        response.setContentType("application/x-www-form-urlencoded");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        response.setHeader("Access-Control-Allow-Methods","GET,POST");

        JSONObject jsonObject = JSON.parseObject(contentJson);
        System.out.println("test04输入值为：" + (String)jsonObject.get("content") + "，" + (String)jsonObject.get("msg") );
        return "这个是输出值";
    }


    @ResponseBody
    @RequestMapping(value = "test05")
    public String test05(@RequestBody String contentJson, HttpServletResponse response){
        // 'content-type': 'application/x-www-form-urlencoded'
      /*  //解决跨域
        response.setContentType("application/json");
        // 设置响应头允许ajax跨域访问
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 星号表示所有的异域请求都可以接受
        response.setHeader("Access-Control-Allow-Methods","GET,POST");*/

        JSONObject jsonObject = JSON.parseObject(contentJson);
        System.out.println("test05输入值为：" + (String)jsonObject.get("test") );
        return "这个是test05输出值";
        // return "这个是输出值";
    }

    @ResponseBody
    @RequestMapping(value = "test06",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test06(@RequestParam String test,@RequestParam String test02, HttpServletResponse response){
        // 'content-type': 'application/x-www-form-urlencoded'
        System.out.println("test06输入值为：" + test + "；" + test02);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","这个是test06输出值");
        return jsonObject.toJSONString();
        // return "这个是输出值";
    }

    @ResponseBody
    @RequestMapping(value = "test07",method = RequestMethod.GET)
    public String test07(@RequestParam String test, HttpServletResponse response){
        // 'content-type': 'application/x-www-form-urlencoded'
        System.out.println("test07输入值为：" + test);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","这个是test07输出值");
        return jsonObject.toJSONString();
        // return "这个是输出值";
    }

    @ResponseBody
    @RequestMapping(value = "test08",produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public String test08(@RequestParam String test,@RequestParam String test02, HttpServletResponse response){
        // 'content-type': 'application/x-www-form-urlencoded'
        System.out.println("test08输入值为：" + test + "；" + test02);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","这个是test08输出值");
        return jsonObject.toJSONString();
        // return "这个是输出值";
    }


    //文件上传处理方法
    @ResponseBody
    @RequestMapping(value="testUpload",method = RequestMethod.POST,produces = "multipart/form-data") //abc.png
    public String testUpload(@RequestParam("desc") String desc , @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request ) throws IOException {

        System.out.println("文件描述信息："+desc);

        if(file.isEmpty()){

            return "所选图片为空";

        }else {

            //jsp中上传的文件：file
            //将file上传到服务器中的 某一个硬盘文件中\
            String fileName = file.getOriginalFilename(); //上传的文件名
            String type = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            System.out.println("图片初始名称为：" + fileName + " 类型为：" + type);

            if (type != null) {

                // 项目在容器中实际发布运行的根路径
            //    String realPath = request.getSession().getServletContext().getRealPath("/WeChatProject");
             //   System.out.println("realPath" + realPath);

                System.out.println(request.getSession().getServletContext().getRealPath("/"));
                System.out.println(request.getServletContext().getRealPath("/WeChatProject"));
                System.out.println(request.getContextPath());
                //获取Tomcat服务器所在的路径
                String tomcat_path = System.getProperty( "user.dir" );
                System.out.println(tomcat_path);
                //获取Tomcat服务器所在路径的最后一个文件目录
                System.out.println(tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length()));

                String realPath = tomcat_path.substring(0,System.getProperty( "user.dir" ).lastIndexOf("\\"));
                System.out.println(realPath);
                // 自定义的文件名称
                String trueFileName = System.currentTimeMillis() + fileName;
                System.out.println("trueFileName" + trueFileName);
                // 设置存放图片文件的路径
                String path = realPath + "/WeChatProjectResourse/" + trueFileName;
                System.out.println("存放图片文件的路径:" + path);
                file.transferTo(new File(path));
                System.out.println("文件成功上传到指定目录下");



                /*OutputStream out = new FileOutputStream("d:\\" + fileName);     //输出流

                byte[] bs = new byte[1024];
                int len = -1;
                while ((len = input.read(bs)) != -1) {
                    out.write(bs, 0, len);
                }
                out.close();
                input.close();*/
                System.out.println("上传成功！");
                return "success";

            }else {
                return "文件类型为空";
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "test10",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test10(@RequestParam(value = "friendIdList[]",required = false) List<Integer> friendIdList, HttpServletResponse response, Map<String,Object> map){

        System.out.println(friendIdList.toString());
        return JSON.toJSONString(friendIdList);
    }

    @ResponseBody
    @RequestMapping(value = "test11",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test11(@RequestParam(value = "friendIdList[]",required = false) int[] friendIdList, HttpServletResponse response, Map<String,Object> map){

        System.out.println(Arrays.toString(friendIdList));
        return JSON.toJSONString(friendIdList);
    }

    @ResponseBody
    @RequestMapping(value = "test12",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test12(@RequestParam(value = "friendIdList[]",required = false) String[] friendIdList, HttpServletResponse response, Map<String,Object> map){

        System.out.println(Arrays.toString(friendIdList));
        return JSON.toJSONString(friendIdList);
    }

    @ResponseBody
    @RequestMapping(value = "test13",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test13(
            Integer[] friendIdList, HttpServletResponse response, Map<String,Object> map) {

        System.out.println(Arrays.toString(friendIdList));
        return JSON.toJSONString(friendIdList);
    }

    @ResponseBody
    @RequestMapping(value = "test14",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test14(
            @RequestParam("friendIdList[]") Integer[] friendIdList, HttpServletResponse response, Map<String,Object> map) {

        System.out.println(Arrays.toString(friendIdList));
        return JSON.toJSONString(friendIdList);
    }

    @ResponseBody
    @RequestMapping(value = "test15",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test15(
            @RequestBody Integer[] friendIdList, HttpServletResponse response, Map<String,Object> map) {

        System.out.println(Arrays.toString(friendIdList));
        return JSON.toJSONString(friendIdList);
    }

    @ResponseBody
    @RequestMapping(value = "test16",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String test16(@RequestParam(value = "friendIdList",required = false) String friendIdList, HttpServletResponse response, Map<String,Object> map) {

        System.out.println(friendIdList);
        List<Integer> ids = JSON.parseArray(friendIdList,Integer.class);
        System.out.println(ids.toString());

        return JSON.toJSONString(ids);
    }


    @ResponseBody
    @RequestMapping(value = "test17")
    public void test16(HttpServletRequest request,HttpServletResponse response, Map<String,Object> map) {

        System.out.println(request.getSession().getServletContext().getRealPath("/WeChatProject_war_exploded"));
        System.out.println(this.getClass().getClassLoader().getResource("").getPath());
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(this.getClass().getResource("/").getPath());
        System.out.println(this.getClass().getClassLoader().getResource("/").getPath());


        System.out.println();
        System.out.println();
        System.out.println();
    }


    @ResponseBody
    @RequestMapping(value = "test18")
    public String test18(HttpServletRequest request,HttpServletResponse response, Map<String,Object> map) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("122","1212");
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "test19")
    public String test19(HttpServletRequest request,HttpServletResponse response, Map<String,Object> map) {

        JSONObject jsonObject = new JSONObject();
        String str = "123";
        jsonObject.put("122",str);
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "test20",produces = "application/json;charset=UTF-8")
    public String test20(HttpServletRequest request,HttpServletResponse response, Map<String,Object> map) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("122","1212");
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "test21",produces = "application/json;charset=UTF-8")
    public String test21(HttpServletRequest request,HttpServletResponse response, Map<String,Object> map) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",new User());
        jsonObject.put("122","1212");
        return jsonObject.toJSONString();
    }
}
