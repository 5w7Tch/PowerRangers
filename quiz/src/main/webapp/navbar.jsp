<nav class = "navbar">
    <div class = "navDiv">
        <div>
            <p class = "quizName">QuizTime</p>
        </div>

        <ul>
            <li><a href = "/">Home</a></li>
            <li><a href = "/profile">Profile</a></li>
        </ul>
        <%
            if(request.getSession().getAttribute("user")==null){ %>
            <button><a href = "/login" class = "logout">Log In</a></button>
        <% }else{ %>
            <button><a href = "/logout" class = "logout">Log Out</a></button>
        <%}%>
    </div>
</nav>