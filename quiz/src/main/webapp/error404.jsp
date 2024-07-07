<%--
  Created by IntelliJ IDEA.
  User: Achi
  Date: 6/19/2024
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/errorPage.css">
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
</head>
<body>
    <%@include file="navbar.jsp"%>
    <img class="error_wallpaper" src="<%=request.getContextPath()%>/static/icons/error404.jpg" alt="bla">
</body>
</html>
