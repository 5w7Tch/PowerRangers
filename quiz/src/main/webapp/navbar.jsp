<%@ page import="models.USER.User" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/navbarStyles.css">
<nav class = "topNavbar">
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
    <div class = "navDiv">
        <div class="quizNameClass">
            <a href="/"><p class = "quizName">QuizTime</p></a>
        </div>

        <form action="/searchAccount" method="get" class="searchForm">
            <input type="text" name="query" placeholder="Search users..." class = "searchInput">
            <button type="submit" class="userSearchButton">Search</button>
        </form>

        <div class = "userInfoContainer">
            <%if(request.getSession().getAttribute("user")==null){ %>
            <a href="${pageContext.request.contextPath}/login" class="login"><button class = "loginBtn">Log In</button></a>
            <% }else{ %>
            <%
                String username = ((User) request.getSession().getAttribute("user")).getUsername();
                out.print("<p class = \"userName\">" + username + "</p>");
            %>
            <a href = "/logout" class="logout"><button class = "loginBtn">Log Out</button></a>
            <%}%>
        </div>
    </div>
</nav>