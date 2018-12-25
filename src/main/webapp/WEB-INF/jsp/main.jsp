
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>登录成功</title>
</head>
<body>
<h1> 登录成功页面 </h1>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    您是超级管理员。hasRole('ROLE_ADMIN')
</sec:authorize>

<sec:authorize access="hasAuthority('ROLE_ADMIN')">
    您是超级管理员 hasAuthority('ROLE_ADMIN')
</sec:authorize>

<sec:authorize access="hasAuthority('test_admin')">
    您是超级管理员
</sec:authorize>

<sec:authorize access="hasAuthority('user')">
    您是普通用户
</sec:authorize>


</body>
</html>
