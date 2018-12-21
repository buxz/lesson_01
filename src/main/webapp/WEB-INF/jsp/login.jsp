<%--
  Created by IntelliJ IDEA.
  User: 10168
  Date: 2018/12/11
  Time: 8:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆页</title>
</head>
<body>
<h1> 登陆页  </h1>
<form method="post" action="/login" >
    <div>
        <label>
            用户名：<input type="text" name="username"/>
        </label>
    </div>
    <div>
        <label>
            密码：<input type="text" name="password"/>
        </label>
    </div>
    <div>
        <input type="submit" value="登录"/>
    </div>

</form>

</body>
</html>
