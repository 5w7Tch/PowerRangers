<%@ page import="models.quizes.Quiz" %>
<%@ page import="models.quizes.questions.Question" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 02.07.24
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/multiplePageQuizStyles.css">

    <%
        Quiz quiz = (Quiz)session.getAttribute("quiz");
        ArrayList<Question> quests = (ArrayList<Question>) session.getAttribute("questions");
        String practise = (String) session.getAttribute("practise");
    %>
    <title><%=quiz.getName()%></title>

</head>
<body>

<div class = "quizContent" id="quizContent">
    <div class="quiz-container" id="questions">
        <%for(int i = 0; i< quests.size(); i++){%>
            <%=quests.get(i).getQuestion(i)%>
        <%}%>
    </div>
        <div class="button-container">
            <%if(!quiz.isImmediateCorrection() || practise.equals("on")){%>
                <button id="prevButton">Previous</button>
                <button id="nextButton">Next</button>
                <button id="next" STYLE="display: none">Next</button>
            <%}else{%>
                <button id="prevButton" style="display: none">Previous</button>
                <button id="nextButton" style="display: none">Next</button>
                <button id="next">Next</button>
            <%}%>
        </div>

</div>

<div class="info">
    <%if(!practise.equals("on")){%>
        <div id="countdown" class="countDown">Time left: <span id="time">00:00:00</span> </div>
    <%}%>
    <button id="submitQuiz">Submit</button>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/multiplePageScripts.js"></script>

</body>
</html>
