<%@ page import="models.USER.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 30.06.24
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/notificationWindowStyles.css">
    <title>Time Out</title>
</head>
<body>

    <div id="timeoutMessage" class="frame">
        <p>Your Time is Out</p>
        <div class="buttons">
            <button id="retake" class="button" name="<%=((Quiz)session.getAttribute("quiz")).getId()%>"> Retake</button>
            <button id="cancel" class="button" style="background-color: red">Cancel</button>
        </div>
    </div>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/cheatedTimeOut.js"></script>
</body>
</html>
