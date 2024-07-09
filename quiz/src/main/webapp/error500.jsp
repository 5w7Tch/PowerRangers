<%--
  Created by IntelliJ IDEA.
  User: Achi
  Date: 7/8/2024
  Time: 12:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/errorPage.css">
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
</head>
<body style="overflow: hidden">
    <%@include file="navbar.jsp"%>
    <img class="error_wallpaper" src="<%=request.getContextPath()%>/static/icons/error500.png" alt="bla">
</body>
</html>
