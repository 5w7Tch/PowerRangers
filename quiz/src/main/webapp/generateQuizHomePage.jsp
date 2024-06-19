<%@ page import="models.DAO.Dao" %>
<%@ page import="models.USER.Quiz" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="models.USER.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.USER.WritenQuiz" %>
<%@ page import="models.comparators.compareByDate" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: sw1tch
  Date: 17.06.24
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style><%@include file="./styles/quizHomePageStyles.css"%></style>
    <% Quiz quiz = null;
        try {
            quiz = ((Dao)application.getAttribute(Dao.DBID)).getQuiz(request.getParameter("quizid"));
            session.setAttribute("quiz", quiz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }%>
    <title><%=quiz.getName()%></title>
    <%
        ArrayList<WritenQuiz> history = ((Dao)application.getAttribute(Dao.DBID)).getQuizHistory(quiz.getId());
        ArrayList<WritenQuiz> userHistory = new ArrayList();
        for(int i = 0; i< history.size(); i++){
            if(history.get(i).getUserId() == ((User)session.getAttribute("user")).getId()){
                userHistory.add(history.get(i));
            }
        }
    %>
</head>
<body>
    <div class="container-one">
        <div class="name"><%=quiz.getName()%></div>
        <a href="/account?id=<%=quiz.getAuthor()%>"><%=((Dao)application.getAttribute(Dao.DBID)).getUserById(quiz.getAuthor()).getUsername()%></a>
    </div>
    <div class="container-two">
        <div style="width: 68%">
            <div class="rectangle"><%=quiz.getDescription()%></div>
            <div class="container-three" style="width: 140%">
                <div class="container-for-table-parts">
                    <div class="label-container" style="justify-content: center">
                        <h4>Leader Board</h4>
                    </div>
                    <div class="table-container" >
                        <table>
                            <thead>
                            <tr>
                                <th>User</th> <th>Score</th> <th>Spent Time</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                for (int i = 0; i < history.size(); i++) {%>
                            <tr>
                                <td><a href="/account?id=<%=history.get(i).getUserId()%>>"><%=((Dao)application.getAttribute(Dao.DBID)).getUserById(history.get(i).getUserId()).getUsername()%>></a></td>
                                <td><%= history.get(i).getScore() %></td>
                                <td><%= history.get(i).getTime() %></td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="container-for-table-parts" style="padding-left: 40px; padding-right: 40px;">
                    <div class="label-container" style="justify-content: center;">
                        <h4>Last Day Leader Board</h4>
                    </div>
                    <div class="table-container">
                        <table>
                            <thead>
                            <tr>
                                <th>User</th> <th>Score</th> <th>Spent Time</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                // Create a Calendar instance
                                Calendar calendar = Calendar.getInstance();
                                // Subtract 24 hours
                                calendar.add(Calendar.HOUR_OF_DAY, -24);
                                // Get the updated Date object
                                for (int i = 0; i < history.size(); i++) {%>
                                    <%
                                        if(history.get(i).getDate().compareTo(calendar.getTime()) >0){%>
                                        <tr>
                                            <td><a href="/account?id=<%=history.get(i).getUserId()%>>"><%=((Dao)application.getAttribute(Dao.DBID)).getUserById(history.get(i).getUserId()).getUsername()%>></a></td>
                                            <td><%= history.get(i).getScore() %></td>
                                            <td><%= history.get(i).getTime() %></td>
                                        </tr>
                                    <%}%>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="container-for-table-parts">
                    <div class="label-container" style="justify-content: center;">
                        <h4>Recent Test Takers</h4>
                    </div>
                    <div class="table-container">
                        <table>
                            <thead>
                            <tr>
                                <th>User</th> <th>Score</th> <th>Spent Time</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                history.sort(new compareByDate());
                                for (int i = 0; i < history.size(); i++) {%>
                            <tr>
                                <td><a href="/account?id=<%=history.get(i).getUserId()%>>"><%=((Dao)application.getAttribute(Dao.DBID)).getUserById(history.get(i).getUserId()).getUsername()%>></a></td>
                                <td><%= history.get(i).getScore() %></td>
                                <td><%= history.get(i).getTime() %></td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>


        <div class="buttons">
            <button type="button" id="start">Start</button>
            <% if(quiz.isPracticable()) {%>
                <button type="button" id="practise">Practise</button>
            <%}%>
            <% if(quiz.getAuthor() == ((User)session.getAttribute("user")).getId()) {%>
                <button type="button" id="edit">Edit</button>
            <%}%>
        </div>
        <div class="container-for-table-parts">
            <div class="label-container">
                <h4>Your History</h4>
                <h6>Order By</h6>
                <div class="radio-buttons">
                    <label><input type="radio" onselect="updateTable()" name="options" value="Score">Score</label>
                    <label><input type="radio" onselect="updateTable()" name="options" value="Time">Time</label>
                    <label><input type="radio" onselect="updateTable()" name="options" value="Date">Date</label>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Score</th> <th>Spent time</th> <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (int i = 0; i < userHistory.size(); i++) {%>
                            <tr>
                                <td><%= userHistory.get(i).getScore() %></td>
                                <td><%= userHistory.get(i).getTime() %></td>
                                <td><%= userHistory.get(i).getDate() %></td>
                            </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <div class="label-container" style="justify-content: center">
                <h4 style="color: blue">Summary stats</h4>
            </div>
            <div class="rectangle" style="height: 220px; border: 2px solid blue; display: flow; text-align: left ">
                <%if(history.size()>0){%>
                    <h5>Average Score: <%= WritenQuiz.getAvgScore(history) %></h5>
                    <h5>Last Written: <%= WritenQuiz.getLastWritenDate(history) %></h5>
                    <h5>Total Written count: <%=history.size()%></h5>
                    <h5>Average Time Spent: <%= WritenQuiz.getAvgTime(history) %></h5>
                    <%User top = ((Dao)application.getAttribute(Dao.DBID)).getUserById(WritenQuiz.getTopScorer(history));%>
                    <h5>Top Scorer: <a href="/account?id=<%=top.getId()%>>"><%=top.getUsername()%>></a> </h5>
                <%}else{%>
                    <h5>Average Score: Not Available</h5>
                    <h5>Last Written: Not Available</h5>
                    <h5>Total Written count: Not Available</h5>
                    <h5>Average Time Spent: Not Available</h5>
                    <h5>Top Scorer: Not Available</h5>
                <%}%>
            </div>
        </div>
    </div>
    <script><%@include file="./scripts/quizHomePageScripts.js"%></script>
</body>
</html>