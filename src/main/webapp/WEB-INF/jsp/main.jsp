
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1> 首页 </h1>
<sec:authorize access="hasRole('admin')">
    您是超级管理员
</sec:authorize>

<sec:authorize access="hasRole('user')">
    您是普通用户
</sec:authorize>


</body>
</html>
