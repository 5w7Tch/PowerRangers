<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/navbarStyles.css">
<nav class = "navbar">
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
    <div class = "navDiv">
        <div>
            <a href="/"><p class = "quizName">QuizTime</p></a>
        </div>
        <ul>
            <li><a href = "/">Home</a></li>
        </ul>
        <%
            if(request.getSession().getAttribute("user")==null){ %>
            <button><a href = "/login" class = "logout">Log In</a></button>
        <% }else{ %>
            <button><a href = "/logout" class = "logout">Log Out</a></button>
        <%}%>
    </div>
</nav>