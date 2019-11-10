<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/10/2
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <form action="loginController/login" method="post">
      userJSON:{"user_name":"张三","user_password":"123"}<input type="text" name="userJson">
      <input type="submit" value="登录">
  </form>

  ${sessionScope.loginUser}
  ${requestScope.msg}





测试：
  输入都为：{"content":"XXX"}     返回值为字符串
  地址：
  http://160bacbd.nat123.cc/WeChatProject_war_exploded/loginController/test01

  http://160bacbd.nat123.cc/WeChatProject_war_exploded/loginController/test02


  http://160bacbd.nat123.cc/WeChatProject_war_exploded/loginController/test03

  http://160bacbd.nat123.cc/WeChatProject_war_exploded/loginController/test04


  这个先不用输入
  ws://160bacbd.nat123.cc/WeChatProject_war_exploded/chatSocket

<br/>
  <form action="loginController/test01" method="post">
    {"content":"XXX"} <input type="text" name="content">
    <input type="submit" value="登录">
  </form>
${requestScope.content}

  </body>
</html>
