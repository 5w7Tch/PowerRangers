<%@ page import="models.USER.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.questions.Question" %><%--
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/onePageQuizStyles.css">

    <%
        Quiz quiz = (Quiz)session.getAttribute("quiz");
        ArrayList<Question> quests = (ArrayList<Question>) session.getAttribute("questions");
    %>
    <title><%=quiz.getName()%></title>

</head>
<body>

<div id="timeoutMessage" class="timeOut">
    <p>Your Time is Out</p>
    <button id="retakeQuiz">Retake</button>
    <button id="goToAccount">Cancel</button>
</div>

<div id="cheatedMessage" class="timeOut">
    <p>Visibility change detected! possibility of cheating Quiz! canceled!</p>
    <button id="retake">Retake</button>
    <button id="Account">Cancel</button>
</div>

<div id="quizContent">
    <div class="info">
        <div id="countdown" class="countDown">Time left: <span id="time">00:00:00</span> </div>
        <button id="submitQuiz">Submit</button>
    </div>

    <div class="quiz-container">
        <%for(int i = 0; i< quests.size(); i++){%>
                <%=quests.get(i).getQuestion()%>
        <%}%>
    </div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/onePageQuizScripts.js"></script>

</body>
</html>


