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
<div class="container">
    <div class="name"><%=quiz.getName()%></div>
    <a href="/account?id=<%=quiz.getAuthor()%>"><%=((Dao)application.getAttribute(Dao.DBID)).getUserById(quiz.getAuthor()).getUsername()%></a>
</div>
<div class="container">
    <div class="rectangle">
        <%=quiz.getDescription()%>
    </div>
    <div class="buttons">
        <button type="button" id="SignUp">Sign Up</button>
    </div>
</div>
</body>
</html>