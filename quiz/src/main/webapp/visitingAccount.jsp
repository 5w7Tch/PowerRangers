<%@ page import="models.USER.User" %><%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 07.07.24
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        User user = (User)session.getAttribute("visitingUser");
    %>
    <title><%=user.getUsername()%></title>
</head>
<body>

</body>
</html>
