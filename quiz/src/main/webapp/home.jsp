<%@ page import="models.USER.User" %><%--
  Created by IntelliJ IDEA.
  User: Achi
  Date: 6/15/2024
  Time: 8:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1><%=   ((User) request.getSession().getAttribute("user")).getUsername() %></h1>
</body>
</html>
