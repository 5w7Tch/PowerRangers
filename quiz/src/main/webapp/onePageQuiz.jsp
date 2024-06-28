<%@ page import="models.USER.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 28.06.24
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Page</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/onePageQuizStyles.css">

</head>
<body>

<div id="timeoutMessage" class="timeOut">
    <p>Your Time is Out</p>
    <button id="retakeQuiz">Retake</button>
    <button id="goToAccount">Cancel</button>
</div>

<div id="quizContent">
    <div id="countdown" class="countDown">Time left: <span id="time"><%=((Quiz)session.getAttribute("quiz")).getDuration()%></span> </div>

    <!-- Your quiz content here -->
    <button id="submitQuiz">Submit</button>

</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/onePageQuizScripts.js"></script>
</body>
</html>

