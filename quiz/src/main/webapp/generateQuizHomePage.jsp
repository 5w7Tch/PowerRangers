<%@ page import="models.DAO.Dao" %>
<%@ page import="models.USER.Quiz" %>
<%@ page import="java.sql.SQLException" %><%--
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }%>
    <title><%=quiz.getName()%></title>

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
                            <tr>
                                <td><a href="/account?id=id">replace</a></td> <td>30</td> <td>5</td>
                            </tr>
                            <tr>
                                <td><a href="/account?id=id">replace</a></td> <td>30</td> <td>50</td>
                            </tr>
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
                            <tr>
                                <td><a href="/account?id=id">replace</a></td> <td>30</td> <td>5</td>
                            </tr>
                            <tr>
                                <td><a href="/account?id=id">replace</a></td> <td>30</td> <td>50</td>
                            </tr>
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
                            <tr>
                                <td><a href="/account?id=id">replace</a></td> <td>30</td> <td>5</td>
                            </tr>
                            <tr>
                                <td><a href="/account?id=id">replace</a></td> <td>30</td> <td>50</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>


        <div class="buttons">
            <button type="button" id="start">Start</button>
            <button type="button" id="practise">practise</button>
        </div>


        <div class="container-for-table-parts">
            <div class="label-container">
                <h4>Your History</h4>
                <h6>Order By</h6>
                <div class="radio-buttons">
                    <label><input type="radio" name="options" value="Score">Score</label>
                    <label><input type="radio" name="options" value="Time">Time</label>
                    <label><input type="radio" name="options" value="Date">Date</label>
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
                    <tr>
                        <td>65%</td> <td>30</td> <td>30/06/2024</td>
                    </tr>
                    <tr>
                        <td>78%</td> <td>25</td> <td>3/05/2024</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="label-container" style="justify-content: center">
                <h4 style="color: blue">Summary stats</h4>
            </div>
            <div class="rectangle" style="height: 220px; border: 2px solid blue; display: flow; text-align: left ">
                <h5>Average Score: replace</h5>
                <h5>Last Written: replace</h5>
                <h5>Total Written count: replace</h5>
                <h5>Average Time Spent: replace</h5>
                <h5>Top Scorer: <a href="/account?id=id">replace</a> </h5>
            </div>
        </div>
    </div>
</body>
</html>