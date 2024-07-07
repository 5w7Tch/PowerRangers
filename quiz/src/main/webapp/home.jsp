<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.USER.User" %>
<%@ page import="models.DAO.Dao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.notification.abstractions.INotification" %>
<%@ page import="models.enums.NotificationType" %>
<%@ page import="models.notification.abstractions.INote" %>
<%@ page import="models.notification.Note" %>
<%@ page import="models.notification.abstractions.IChallenge" %>
<%@ page import="models.notification.Challenge" %>
<%@ page import="models.friend.abstractions.IFriendRequest" %>
<%@ page import="models.friend.FriendRequest" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="models.achievement.abstractions.IAchievement" %>
<%@ page import="models.USER.WritenQuiz" %>
<%@ page import="static java.lang.Math.max" %>
<%@ page import="static java.lang.Math.min" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/homePageStyles.scss">
    <title>Home</title>
    <%
        Dao myDb = (Dao)application.getAttribute(Dao.DBID);
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<INotification> notifications = myDb.getUserNotifications(user.getId());
        ArrayList<IAchievement> achievements = myDb.getUserAchievements(user.getId());
        ArrayList<WritenQuiz> userQuizActivity = myDb.getUserQuizActivity(user.getId());
    %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript">
        function acceptChallenge(quizId) {
            window.location.href = `<%= request.getContextPath() %>/quiz?quizid=` + encodeURIComponent(quizId);
        }
        function handleFriendRequest(request, friendRequestId, index) {
            let div = document.getElementsByClassName('friend-request-info')[index];
            div.classList.remove('justify-content-between');
            div.classList.add('justify-content-center');
            div.classList.add('align-items-center');
            let args = {
                'friendRequestId': friendRequestId,
            }
            $.ajax({
                url: `/`+request+`FriendRequest`,
                type: 'POST',
                data: JSON.stringify(args),
                contentType: 'application/json; charset=UTF-8',
                beforeSend: function (xhr){
                    //
                },
                success: function (result,status,xhr){
                    div.innerHTML = `<p class="mx-auto">Friend Request `+request+`ed</p>`
                },
                error: function (xhr,status,error){
                    div.classList.add('justify-content-between');
                    div.classList.remove('justify-content-center');
                    div.classList.remove('align-items-center');
                }
            })
        }
    </script>
</head>
<body>
    <div class="container-fluid bg-primary h-50">
        <div class="row bg-secondary h-75">
            <div class="col-3">
                <div class="row user bg-info">
                    <div class="col-9 align-content-center">
                        <h1><%=user.getUsername() %></h1>
                    </div>
                    <div class="col-3 d-flex justify-content-center align-items-center">
                        <button type="button" id="notificationsButton" class="btn btn-secondary">
                            <i class="bi bi-bell"></i>
                        </button>
                        <div id="notificationsList" class="notifications-list shadow-lg p-3 bg-body rounded" aria-labelledby="notificationsButton">
                            <div class="list-group notification-list">
                                <%
                                    int i = 0;
                                    for (INotification notification : notifications) {
                                        SimpleDateFormat dayFormat = new SimpleDateFormat("dd, EE"); // EEEE for full day name
                                        String dayName = dayFormat.format(notification.getSendTime());
                                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                        String time = timeFormat.format(notification.getSendTime());
                                        User fromUser = myDb.getUserById(notification.getFromId());
                                %>
                                <div class="notification-wrapper mb-1 bg-info border-bottom border-info border-2 rounded-top">
                                    <div class="d-flex justify-content-between">
                                        <h6 class="mb-1">From <%=fromUser.getUsername()%>: <mark><%=notification.getType().getDisplayName()%></mark></h6>
                                        <p  class="mb-1"><%=dayName%>, <%=time%></p>
                                    </div>
                                <%
                                        if (notification.getType() == NotificationType.NOTE) {
                                            assert notification instanceof Note;
                                            INote note = (Note) notification;
                                %>
                                    <div class="note-info shadow-sm">
                                        <p><%=note.getText()%></p>
                                    </div>
                                <%
                                        }
                                        if (notification.getType() == NotificationType.CHALLENGE) {
                                            assert notification instanceof Challenge;
                                            IChallenge challenge = (Challenge) notification;
                                %>
                                    <div class="challenge-info d-flex justify-content-center">
                                        <button onclick="acceptChallenge(<%=challenge.getQuizId()%>)" class="btn btn-outline-primary rounded-0 mx-auto mt-2">Accept Challenge</button>
                                    </div>
                                <%
                                        }
                                        if (notification.getType() == NotificationType.FRIEND_REQUEST) {
                                            assert notification instanceof FriendRequest;
                                            IFriendRequest friendRequest = (FriendRequest) notification;
                                %>
                                    <div class="friend-request-info d-flex justify-content-between">
                                        <button onclick="handleFriendRequest('Accept', <%=friendRequest.getId()%>, <%=i%>)" class="btn btn-outline-primary px-4 rounded-0 mx-auto mt-2">Accept</button>
                                        <button onclick="handleFriendRequest('Reject', <%=friendRequest.getId()%>, <%=i%>)" class="btn btn-outline-secondary px-4 rounded-0 mx-auto mt-2">Reject</button>
                                    </div>
                                <%
                                        i++;
                                        }
                                %>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row achievements bg-info mb-3">
                    <div class="col-12 p-0 card rounded-0">
                        <div class="card-header">
                            Achievements
                        </div>
                        <div class="achievement-container-wrapper bg-info">
                            <div class="card-body p-0 m-2 d-flex overflow-auto achievement-container">
                                <%
                                if(achievements.isEmpty()) {
                                %>
                                <p>You have no achievements :(</p>
                                <%
                                }
                                %>
                                <%
                                    for(i = 0; i < achievements.size(); i++) {
                                %>
                                <div class="achievement-icon text-white d-flex align-items-center justify-content-center" data-bs-toggle="modal" data-bs-target="#achievementModal<%=i%>">
                                    <img src="<%=request.getContextPath()%><%=achievements.get(i).getIcon()%>" class="img-thumbnail" alt="<%=achievements.get(i).getType().getDisplayName()%>>" data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<%=achievements.get(i).getType().getDisplayName()%>">
                                </div>
                                <div class="modal" id="achievementModal<%=i%>" tabindex="-1" aria-labelledby="Achievement Description" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="achievementModalLabel"><%=achievements.get(i).getType().getDisplayName()%></h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="bg-info modal-body d-grid justify-content-center">
                                                <div class="card" style="width: 18rem;">
                                                    <img src="<%=request.getContextPath()%><%=achievements.get(i).getIcon()%>" class="img-thumbnail card-img-top" alt="<%=achievements.get(i).getType().getDisplayName()%>>">
                                                    <div class="card-body">
                                                        <p class="card-text"><%=achievements.get(i).getDescription()%></p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row quizActivities bg-warning mb-3">
                    <div class="col-12 p-0 card rounded-0">
                        <div class="card-header">
                            Your Quiz Activity
                        </div>
                        <div class="quizActivities-container-wrapper bg-info">
                            <div class="card-body p-0 m-2 d-flex flex-column quizActivities-container">
                                <%if(userQuizActivity.isEmpty()){%>
                                <p>You have no activity :(</p>
                                <%}else{%>
                                <table class="table table-info table-striped table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Quiz</th>
                                        <th scope="col">Creator</th>
                                        <th scope="col">Score</th>
                                        <th scope="col">Spent Time</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (i = 0; i < min(userQuizActivity.size(), 5); i++) {%>
                                    <tr>
                                        <td><%=i + 1%></td>
                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= userQuizActivity.get(i).getQuizId() %>`"><%= userQuizActivity.get(i).getQuizId() %></a></td>
                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/account?id=<%=userQuizActivity.get(i).getUserId()%>"><%=userQuizActivity.get(i).getWriterName()%></a></td>
                                        <td><%= userQuizActivity.get(i).getScoreString() %></td>
                                        <td><%= userQuizActivity.get(i).getTimeString() %></td>
                                    </tr>
                                    <%}%>
                                    </tbody>
                                </table>
                                <button class="btn btn-link align-self-end" data-bs-toggle="modal" data-bs-target="#quizActivityModal">see more</button>
                                <div class="modal" id="quizActivityModal" tabindex="-1" aria-labelledby="Quiz Activity Description" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="quizActivityModalLabel">Your Quiz Activity</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="bg-secondary modal-body d-grid justify-content-center">
                                                <table class="table table-info table-striped table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th scope="col">#</th>
                                                        <th scope="col">Quiz</th>
                                                        <th scope="col">Creator</th>
                                                        <th scope="col">Score</th>
                                                        <th scope="col">Spent Time</th>
                                                        <th scope="col">Date</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <%
                                                        for (i = 0; i < userQuizActivity.size(); i++) {%>
                                                    <tr>
                                                        <td><%=i + 1%></td>
                                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= userQuizActivity.get(i).getQuizId() %>`"><%= userQuizActivity.get(i).getQuizId() %></a></td>
                                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/account?id=<%=userQuizActivity.get(i).getUserId()%>"><%=userQuizActivity.get(i).getWriterName()%></a></td>
                                                        <td><%= userQuizActivity.get(i).getScoreString() %></td>
                                                        <td><%= userQuizActivity.get(i).getTimeString() %></td>
                                                        <td><%= userQuizActivity.get(i).getDateString() %></td>
                                                    </tr>
                                                    <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row createdQuizzes bg-info h-25"></div>
            </div>
            <div class="col-6 bg-danger h-75">

            </div>
            <div class="col-3 bg-success h-75">
                <div class="row popularQuizzes bg-warning h-25"></div>
                <div class="row recentQuizzes bg-info h-25"></div>
                <div class="row announcements bg-warning h-50"></div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js" integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
    <script type="module" src="<%=request.getContextPath()%>/static/scripts/homePage.js"></script>
</body>
</html>
