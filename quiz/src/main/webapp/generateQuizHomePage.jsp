<%@ page import="models.DAO.Dao" %>
<%@ page import="models.USER.Quiz" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="models.USER.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.USER.WritenQuiz" %>
<%@ page import="models.comparators.compareByDate" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.mysql.cj.conf.ConnectionUrlParser" %><%--
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
    <style><%@include file="./styles/navbarStyles.css"%></style>

    <%
            Dao myDb = (Dao)application.getAttribute(Dao.DBID);
            Quiz quiz = myDb.getQuiz(request.getParameter("quizid"));
        %>
    <title><%=quiz.getName()%></title>
    <%
        ArrayList<WritenQuiz> history = myDb.getQuizHistory(quiz.getId());
        session.setAttribute("history", history);
        ArrayList<WritenQuiz> userHistory = new ArrayList<WritenQuiz>();
        for(int i = 0; i< history.size(); i++){
            if(history.get(i).getUserId() == ((User)session.getAttribute("user")).getId()){
                userHistory.add(history.get(i));
            }
        }
    %>
</head>
<body>
<%@ include file="./html/navbar.html" %>

    <div class="container-one">
        <div class="name"><%=quiz.getName()%></div>
        <a href="/account?id=<%=quiz.getAuthor()%>"><%=myDb.getUserById(quiz.getAuthor()).getUsername()%></a>
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
                                <td><a href="/account?id=<%=history.get(i).getUserId()%>"><%=history.get(i).getWriterName()%></a></td>
                                <td><%= history.get(i).getScoreString() %></td>
                                <td><%= history.get(i).getTimeString()%></td>
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
                                            <td><a href="/account?id=<%=history.get(i).getUserId()%>"><%=history.get(i).getWriterName()%></a></td>
                                            <td><%= history.get(i).getScoreString() %></td>
                                            <td><%= history.get(i).getTimeString() %></td>
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
                                <td><a href="/account?id=<%=history.get(i).getUserId()%>"><%=history.get(i).getWriterName()%></a></td>
                                <td><%= history.get(i).getScoreString() %></td>
                                <td><%= history.get(i).getTimeString() %></td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>


        <div class="buttons">
            <button type="button" id="startSinglePage">Start Single Page</button>
            <button type="button" id="startMultiPage">Start Multi Page</button>
            <% if(quiz.isPracticable()) {%>
                <button type="button" id="practise" style="background-color: blue">Practise</button>
            <%}%>
            <% if(quiz.getAuthor() == ((User)session.getAttribute("user")).getId()) {%>
                <button type="button" id="edit" style="background-color: gray">Edit</button>
            <%}%>
            <% if(((User)session.getAttribute("user")).isAdmin()) {%>
            <button type="button" id="Delete" style="background-color: red">Delete Quiz</button>
            <%}%>
        </div>
        <div class="container-for-table-parts">
            <div class="label-container">
                <h4>Your History</h4>
                <h6>Order By</h6>
                <div class="radio-buttons">
                    <label><input type="radio" onchange="update()" name="options" value="Score">Score</label>
                    <label><input type="radio" onchange="update()" name="options" value="Time">Time</label>
                    <label><input type="radio" onchange="update()" name="options" value="Date">Date</label>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Score</th> <th>Spent time</th> <th>Date</th>
                    </tr>
                    </thead>
                    <tbody id="tableBody">
                    <%
                        for (int i = 0; i < userHistory.size(); i++) {%>
                            <tr>
                                <td><%= userHistory.get(i).getScoreString() %></td>
                                <td><%= userHistory.get(i).getTimeString() %></td>
                                <td><%= userHistory.get(i).getDateString() %></td>
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
                    <%ConnectionUrlParser.Pair<Integer, String> top = WritenQuiz.getTopScorer(history);%>
                    <h5>Top Scorer: <a href="/account?id=<%=top.left%>"><%=top.right%></a></h5>
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