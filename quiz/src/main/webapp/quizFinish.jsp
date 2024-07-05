<%@ page import="models.USER.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.USER.User" %>
<%@ page import="models.questions.Question" %>
<%@ page import="models.USER.WritenQuiz" %>
<%@ page import="models.DAO.Dao" %><%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 03.07.24
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/quizResultStyles.css">
    <%
        Quiz quiz = (Quiz)session.getAttribute("quiz");
        ArrayList<Double> results = (ArrayList<Double>)session.getAttribute("results");
        User user = (User)session.getAttribute("user");
        ArrayList<Question> quests = (ArrayList<Question>) session.getAttribute("questions");
        Dao myDb = (Dao)application.getAttribute(Dao.DBID);
        ArrayList<WritenQuiz> friendHistory = myDb.getFriendHistory(quiz.getId(), user.getId());

    %>
    <title>Quiz result</title>
</head>
<body>
<%@ include file="navbar.jsp" %>

    <div class="container">
        <div class="quizBlock">
            <%for (int i = 0; i < quests.size(); i++) {%>
                <div class="questResult">
                    <%=results.get(i)%>/<%=quests.get(i).getScore()%>
                    <div class="container">
                        <div class="question-box">
                            <%=quests.get(i).getQuestion()%>
                        </div>
                        <%if(!results.get(i).equals(quests.get(i).getScore()) && results.get(i).equals(0.0)){%>
                            <img src="icons/wrong.png" alt="wrong">
                        <%}else{%>
                            <img src="icons/check.png" alt="correct">
                        <%}%>
                    </div>
                </div>
            <%}%>
        </div>
        <div style="justify-content: center">
            <div class="rectangle">
                <h3 STYLE="color: red">Score:<label style="color: black"><%=session.getAttribute("score")%>/<%=(Double)session.getAttribute("realScore")%></label></h3>
                <h3 STYLE="color: red">Time Spent Minutes:<label style="color: black"><%=session.getAttribute("timeSpent")%></label></h3>
                <div class="buttons">
                    <button id="retake" class="button" name="<%=((Quiz)session.getAttribute("quiz")).getId()%>"> Retake</button>
                    <button id="cancel" class="button">Home</button>
                </div>
            </div>
            <div class="container-for-table-parts">
                <div class="label-container" style="justify-content: center;">
                    <h4>Friend results</h4>
                </div>
                <div class="table-container">
                    <table>
                        <thead>
                        <tr>
                            <th>Friend</th> <th>Score</th> <th>Spent Time</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%

                            for (int i = 0; i < friendHistory.size(); i++) {%>
                                <tr>
                                    <td><a href="/account?id=<%=friendHistory.get(i).getUserId()%>"><%=friendHistory.get(i).getWriterName()%></a></td>
                                    <td><%= friendHistory.get(i).getScoreString() %></td>
                                    <td><%= friendHistory.get(i).getTimeString() %></td>
                                </tr>
                        <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/cheatedTimeOut.js"></script>
</body>
</html>
