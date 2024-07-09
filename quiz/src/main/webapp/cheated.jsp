<%@ page import="models.quizes.Quiz" %><%--
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
    <title>Possible Cheating Detected</title>
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
</head>
<body>
    <div id="cheatedMessage" class="frame">
        <p>Window change detected!</p>
        <p>Possibility of cheating Quiz!</p>
        <p>Quiz Canceled!</p>
        <div class="buttons">
            <button id="retake" class="button" name="<%=((Quiz)session.getAttribute("quiz")).getId()%>"> Retake</button>
            <button id="cancel" class="button" style="background-color: red">Cancel</button>
        </div>
    </div>

    <script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/cheatedTimeOut.js"></script>
</body>
</html>
