<%@ page import="models.USER.User" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/navbarStyles.css">
<nav class = "topNavbar">
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
    <div class = "navDiv">
        <div class="quizNameClass">
            <a href="/"><p class = "quizName">QuizTime</p></a>
        </div>

        <form action="${pageContext.request.contextPath}/searchAccount" method="post" class="searchForm">
            <input type="text" name="query" placeholder="Search users..." class = "searchInput" id = "searchInput">
            <button type="submit" class="userSearchButton">Search</button>
            <div id="suggestions" class="suggestions"></div>
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

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var searchInput = document.getElementById('searchInput');
        searchInput.addEventListener('input', function() {
            var query = this.value;
            if (query.length > 1) {
                fetch('<%=request.getContextPath()%>/searchAccount?query=' + query)
                    .then(response => response.json())
                    .then(data => {
                        var suggestions = document.getElementById('suggestions');
                        suggestions.innerHTML = '';
                        data.forEach(user => {
                            var div = document.createElement('div');
                            div.className = 'suggestion-item';
                            div.innerHTML = user.username;
                            div.addEventListener('click', function() {
                                searchInput.value = user.username;
                                suggestions.innerHTML = '';
                            });
                            suggestions.appendChild(div);
                        });
                    });
            } else {
                document.getElementById('suggestions').innerHTML = '';
            }
        });
    });
</script>>