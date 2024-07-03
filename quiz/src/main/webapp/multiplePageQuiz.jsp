<%@ page import="models.USER.Quiz" %>
<%@ page import="models.questions.Question" %>
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
    %>
    <title><%=quiz.getName()%></title>

</head>
<body>

<div class = "quizContent" id="quizContent">
    <div class="quiz-container" id="questions">
        <div class="question-box">
            <div class="question-text">question?</div>
            <ul class="answers">
                <div class="answer_radio" onclick="radioChange(this, 0)" name="0">Berlin</div>
                <div class="answer_radio" onclick="radioChange(this, 0)" name="0">Madrid</div>
                <div class="answer_radio" onclick="radioChange(this, 0)" name="0">Paris</div>
                <div class="answer_radio" onclick="radioChange(this, 0)" name="0">Lisbon</div>
            </ul>
        </div>
        <div class="question-box">
            <div class="question-text">question?</div>
            <ul class="answers">
                <div class="answer_response" contenteditable="true"name="1" onchange="questionResponseChange(1)"></div>
            </ul>
        </div>
        <div class="question-box">
            <div class="question-text">Fill in!</div>
            <div class="question-text">
                bla bla bla
                <div class="answer_response" contenteditable="true" name="2" onchange="fillInChange(2)"></div>
                bla bla bla
            </div>
        </div>
        <div class="question-box">
            <div class="question-text">Match.</div>
            <div class="match">
                <ul class="answers">
                    <h5>1. <div class="match_option">iyo arabets</div></h5>
                    <h5>2. <div class="match_option">iao uao</div></h5>
                </ul>
                <ul class="answers">
                    <h5>a. <div class="match_option">bebiastan mivdivar</div></h5>
                    <h5>b. <div class="match_option">juzepe</div></h5>
                </ul>
            </div>
            <div class="dropdown-container">
                <h5>1.
                    <div class="answer_response" contenteditable="true"name="5" onchange="matchChange(5)"></div>
                </h5>
                <h5>2.
                    <div class="answer_response" contenteditable="true"name="5" onchange="matchChange(5)"></div>
                </h5>
            </div>
        </div>

<%--        <%for(int i = 0; i< quests.size(); i++){%>--%>
<%--        <%=quests.get(i).getQuestion()%>--%>
<%--        <%}%>--%>
    </div>

        <div class="button-container">
            <%if(quiz.isImmediateCorrection()){%>
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
</div>
<div class="info">
    <div id="countdown" class="countDown">Time left: <span id="time">00:00:00</span> </div>
    <button id="submitQuiz">Submit</button>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/multiplePageScripts.js"></script>

</body>
</html>
