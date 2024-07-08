<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.USER.User" %>
<%@ page import="models.DAO.Dao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.notification.abstractions.INotification" %>
<%@ page import="models.enums.ActivityType" %>
<%@ page import="models.notification.abstractions.INote" %>
<%@ page import="models.notification.Note" %>
<%@ page import="models.notification.abstractions.IChallenge" %>
<%@ page import="models.notification.Challenge" %>
<%@ page import="models.friend.abstractions.IFriendRequest" %>
<%@ page import="models.friend.FriendRequest" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="models.achievement.abstractions.IAchievement" %>
<%@ page import="models.USER.WritenQuiz" %>
<%@ page import="static java.lang.Math.min" %>
<%@ page import="models.quizes.Quiz" %>
<%@ page import="models.announcement.abstractions.IAnnouncement" %>
<%@ page import="models.activity.abstractions.IActivity" %>
<%@ page import="models.announcement.Announcement" %>
<%@ page import="models.friend.Friend" %>
<%@ page import="models.friend.abstractions.IFriend" %>
<%@ page import="models.achievement.Achievement" %>
<%@ page import="models.achievement.abstractions.IUserAchievement" %>
<%@ page import="models.achievement.UserAchievement" %>
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
        ArrayList<Quiz> userCreatedQuizzes = myDb.getUserCreatedQuizzes(user.getId());
        ArrayList<Quiz> popularQuizzes = myDb.getPopularQuizzes();
        ArrayList<Quiz> recentQuizzes = myDb.getRecentQuizzes();
        ArrayList<IAnnouncement> announcements = myDb.getAnnouncements();
        ArrayList<IActivity> activities = myDb.getFriendsActivity(user.getId());
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
    <%@ include file="navbar.jsp" %>
    <div class="container-fluid">
        <div class="row" style="min-height: 100vh;">
            <div class="col-3 page-columns bg-info bg-opacity-25">
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
                                        if (notification.getType() == ActivityType.NOTE) {
                                            assert notification instanceof Note;
                                            INote note = (Note) notification;
                                    %>
                                    <div class="note-info shadow-sm">
                                        <p><%=note.getText()%></p>
                                    </div>
                                    <%
                                        }
                                        if (notification.getType() == ActivityType.CHALLENGE) {
                                            assert notification instanceof Challenge;
                                            IChallenge challenge = (Challenge) notification;
                                    %>
                                    <div class="challenge-info d-flex justify-content-center">
                                        <button onclick="acceptChallenge(<%=challenge.getQuizId()%>)" class="btn btn-outline-primary rounded-0 mx-auto mt-2">Accept Challenge</button>
                                    </div>
                                    <%
                                        }
                                        if (notification.getType() == ActivityType.FRIEND_REQUEST) {
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
                <div class="row achievements mb-3">
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
                                                        <p class="card-text" style="word-wrap: break-word; white-space: pre-wrap;"><%=achievements.get(i).getDescription()%></p>
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
                <div class="row quizActivities mb-3">
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
                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= userQuizActivity.get(i).getQuizId() %>"><%= userQuizActivity.get(i).getQuizId() %></a></td>
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
                                            <div class="bg-secondary modal-body d-grid">
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
                                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= userQuizActivity.get(i).getQuizId() %>"><%= userQuizActivity.get(i).getQuizId() %></a></td>
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
                <div class="row createdQuizzes">
                    <div class="col-12 p-0 card rounded-0">
                        <div class="card-header">
                            Your Created Quizzes
                        </div>
                        <div class="createdQuizzes-container-wrapper bg-info">
                            <div class="card-body p-0 m-2 d-flex flex-column createdQuizzes-container">
                                <%if(userCreatedQuizzes.isEmpty()){%>
                                <p>You haven't created any quiz :(</p>
                                <%}else{%>
                                <table class="table table-info table-striped table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Quiz</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Duration</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (i = 0; i < min(userCreatedQuizzes.size(), 5); i++) {%>
                                    <tr>
                                        <td><%=i + 1%></td>
                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= userCreatedQuizzes.get(i).getId() %>">open</a></td>
                                        <td><%= userCreatedQuizzes.get(i).getName() %></td>
                                        <td><%= userCreatedQuizzes.get(i).getDuration() %></td>
                                    </tr>
                                    <%}%>
                                    </tbody>
                                </table>
                                <%}%>
                                <div class="createdQuizzes-buttons d-flex justify-content-between">
                                    <button id="createQuizBtn" class="btn btn-outline-primary">create new</button>
                                    <%
                                        if(!userCreatedQuizzes.isEmpty()){
                                    %>
                                    <button class="btn btn-link" data-bs-toggle="modal" data-bs-target="#createdQuizzesModal">see more</button>
                                    <%}%>
                                </div>
                                <div class="modal" id="createdQuizzesModal" tabindex="-1" aria-labelledby="Created Quizzes Description" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="createdQuizzesModalLabel">Your Created Quizzes</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="bg-secondary modal-body d-grid">
                                                <table class="table table-info table-striped table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th scope="col">#</th>
                                                        <th scope="col">Quiz</th>
                                                        <th scope="col" style="min-width: 180px" >Name</th>
                                                        <th scope="col">Duration</th>
                                                        <th scope="col" style="min-width: 110px" >Created At</th>
                                                        <th scope="col">Description</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <%
                                                        for (i = 0; i < userCreatedQuizzes.size(); i++) {%>
                                                    <tr>
                                                        <td><%=i + 1%></td>
                                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= userCreatedQuizzes.get(i).getId() %>">open</a></td>
                                                        <td style="min-width: 180px" ><%= userCreatedQuizzes.get(i).getName() %></td>
                                                        <td><%= userCreatedQuizzes.get(i).getDuration() %></td>
                                                        <td style="min-width: 110px" ><%= userCreatedQuizzes.get(i).getCreationDate() %></td>
                                                        <td style="word-break: break-word;"><%= userCreatedQuizzes.get(i).getDescription() %></td>
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-6">
                <div class="list-group activity-list">
                    <%
                        if(activities.isEmpty()){
                    %>
                    <div class="empty-activities bg-info bg-opacity-25">
                        <div>
                            <h4>Add friends to see their activity</h4>
                        </div>
                    </div>
                    <%}%>
                    <%
                        i = 0;
                        for (IActivity activity : activities) {
                            SimpleDateFormat dayFormat = new SimpleDateFormat("dd, EE"); // EEEE for full day name
                            String dayName = dayFormat.format(activity.getSendTime());
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            String time = timeFormat.format(activity.getSendTime());
                            User fromUser = myDb.getUserById(activity.getFromId());
                    %>
                    <div class="activity-wrapper mb-1 bg-info border-bottom border-info border-2 rounded-top">
                        <div class="d-flex mb-2 justify-content-between">
                            <h6 class="mb-1">From <a class="link-primary" href="<%= request.getContextPath() %>/account?id=<%= fromUser.getId() %>"><%=fromUser.getUsername()%></a>: <mark><%=activity.getType().getDisplayName()%></mark></h6>
                            <p  class="mb-1"><%=dayName%>, <%=time%></p>
                        </div>
                        <div class="activity-info d-flex justify-content-center align-items-center">
                        <%
                            if (activity.getType() == ActivityType.ANNOUNCEMENT) {
                                assert activity instanceof Announcement;
                                IAnnouncement announcement = (Announcement) activity;
                        %>
                        <div class="note-info shadow-sm">
                            <p><%=announcement.getText()%></p>
                        </div>
                        <%
                            }
                            if (activity.getType() == ActivityType.CHALLENGE) {
                                assert activity instanceof Challenge;
                                IChallenge challenge = (Challenge) activity;
                        %>
                        <div class="challenge-info shadow-sm">
                            <p><%=fromUser.getUsername()%> challenged <a class="link-primary" href="<%= request.getContextPath() %>/account?id=<%= challenge.getToId() %>"><%=myDb.getUserById(challenge.getToId()).getUsername()%></a> to write <a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= challenge.getQuizId()%>">quiz</a></p>
                        </div>
                        <%
                            }
                            if (activity.getType() == ActivityType.FRIENDSHIP) {
                                assert activity instanceof Friend;
                                IFriend friendship = (Friend) activity;
                        %>
                        <div class="friendship-info shadow-sm">
                            <p><%=fromUser.getUsername()%> and <a class="link-primary" href="<%= request.getContextPath() %>/account?id=<%= friendship.getUserTwoId() %>"><%=myDb.getUserById(friendship.getUserTwoId()).getUsername()%></a> became friends.</p>
                        </div>
                        <%
                            }
                            if (activity.getType() == ActivityType.ACHIEVEMENT) {
                                assert activity instanceof UserAchievement;
                                IUserAchievement userAchievement = (UserAchievement) activity;
                                IAchievement achievement = myDb.getAchievementById(userAchievement.getAchievementId());
                        %>
                        <div class="user-achievement-info shadow-sm">
                            <p><%=fromUser.getUsername()%> gained achievement: <mark><%=achievement.getType().getDisplayName()%></mark></p>
                            <div class="achievement-activity-icon my-3 text-white d-flex align-items-center justify-content-center" data-bs-toggle="modal" data-bs-target="#achievementActivityModal">
                                <img src="<%=request.getContextPath()%><%=achievement.getIcon()%>" class="img-thumbnail" alt="<%=achievement.getType().getDisplayName()%>>" data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<%=achievement.getType().getDisplayName()%>">
                            </div>
                            <div class="modal" id="achievementActivityModal" tabindex="-1" aria-labelledby="Achievement Activity Description" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="achievementActivityModalLabel"><%=achievement.getType().getDisplayName()%></h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="bg-info modal-body d-grid justify-content-center">
                                            <div class="card" style="width: 18rem;">
                                                <img src="<%=request.getContextPath()%><%=achievement.getIcon()%>" class="img-thumbnail card-img-top" alt="<%=achievement.getType().getDisplayName()%>>">
                                                <div class="card-body">
                                                    <p class="card-text" style="word-wrap: break-word; white-space: pre-wrap;"><%=achievement.getDescription()%></p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                            if (activity.getType() == ActivityType.WROTE_QUIZ) {
                                assert activity instanceof WritenQuiz;
                                WritenQuiz writtenQuiz = (WritenQuiz) activity;
                        %>
                        <div class="written-quiz-info shadow-sm">
                            <p><%=fromUser.getUsername()%> wrote <a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= writtenQuiz.getQuizId()%>">quiz</a></p>
                        </div>
                        <%
                            }
                            if (activity.getType() == ActivityType.CREATED_QUIZ) {
                                assert activity instanceof Quiz;
                                Quiz createdQuiz = (Quiz) activity;
                        %>
                        <div class="created-quiz-info shadow-sm">
                            <p><%=fromUser.getUsername()%> created <a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= createdQuiz.getId()%>">quiz</a></p>
                        </div>
                        <%
                            }
                        %>
                        </div>
                    </div>
                    <%
                            i++;
                        }
                    %>
                </div>
            </div>
            <div class="col-3 page-columns bg-info bg-opacity-25">
                <div class="row popularQuizzes mb-3">
                    <div class="col-12 p-0 card rounded-0">
                        <div class="card-header">
                            Popular Quizzes
                        </div>
                        <div class="popularQuizzes-container-wrapper bg-info">
                            <div class="card-body p-0 m-2 d-flex flex-column popularQuizzes-container">
                                <%if(popularQuizzes == null || popularQuizzes.isEmpty()){%>
                                <p>There is no popular quiz :O</p>
                                <%}else{%>
                                <table class="table table-info table-striped table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Quiz</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Duration</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (i = 0; i < min(popularQuizzes.size(), 5); i++) {%>
                                    <tr>
                                        <td><%=i + 1%></td>
                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= popularQuizzes.get(i).getId() %>">open</a></td>
                                        <td><%= popularQuizzes.get(i).getName() %></td>
                                        <td><%= popularQuizzes.get(i).getDuration() %></td>
                                    </tr>
                                    <%}%>
                                    </tbody>
                                </table>
                                <button class="btn btn-link align-self-end" data-bs-toggle="modal" data-bs-target="#popularQuizzesModal">see more</button>
                                <div class="modal" id="popularQuizzesModal" tabindex="-1" aria-labelledby="Popular Quizzes Description" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="popularQuizzesModalLabel">Popular Quizzes</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="bg-secondary modal-body d-grid">
                                                <table class="table table-info table-striped table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th scope="col">#</th>
                                                        <th scope="col">Quiz</th>
                                                        <th scope="col" style="min-width: 180px" >Name</th>
                                                        <th scope="col">Duration</th>
                                                        <th scope="col" style="min-width: 110px" >Created At</th>
                                                        <th scope="col">Description</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <%
                                                        for (i = 0; i < popularQuizzes.size(); i++) {%>
                                                    <tr>
                                                        <td><%=i + 1%></td>
                                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= popularQuizzes.get(i).getId() %>">open</a></td>
                                                        <td style="min-width: 180px" ><%= popularQuizzes.get(i).getName() %></td>
                                                        <td><%= popularQuizzes.get(i).getDuration() %></td>
                                                        <td style="min-width: 110px" ><%= popularQuizzes.get(i).getCreationDate() %></td>
                                                        <td style="word-break: break-word;"><%= popularQuizzes.get(i).getDescription() %></td>
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
                <div class="row recentQuizzes mb-3">
                    <div class="col-12 p-0 card rounded-0">
                        <div class="card-header">
                            Recent Quizzes
                        </div>
                        <div class="recentQuizzes-container-wrapper bg-info">
                            <div class="card-body p-0 m-2 d-flex flex-column recentQuizzes-container">
                                <%if(recentQuizzes == null || recentQuizzes.isEmpty()){%>
                                <p>There is no recent quiz :O</p>
                                <%}else{%>
                                <table class="table table-info table-striped table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Quiz</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Duration</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        for (i = 0; i < min(recentQuizzes.size(), 5); i++) {%>
                                    <tr>
                                        <td><%=i + 1%></td>
                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= recentQuizzes.get(i).getId() %>">open</a></td>
                                        <td><%= recentQuizzes.get(i).getName() %></td>
                                        <td><%= recentQuizzes.get(i).getDuration() %></td>
                                    </tr>
                                    <%}%>
                                    </tbody>
                                </table>
                                <button class="btn btn-link align-self-end" data-bs-toggle="modal" data-bs-target="#recentQuizzesModal">see more</button>
                                <div class="modal" id="recentQuizzesModal" tabindex="-1" aria-labelledby="Recent Quizzes Description" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="recentQuizzesModalLabel">Recent Quizzes</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="bg-secondary modal-body d-grid">
                                                <table class="table table-info table-striped table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th scope="col">#</th>
                                                        <th scope="col">Quiz</th>
                                                        <th scope="col" style="min-width: 180px" >Name</th>
                                                        <th scope="col">Duration</th>
                                                        <th scope="col" style="min-width: 110px" >Created At</th>
                                                        <th scope="col">Description</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <%
                                                        for (i = 0; i < recentQuizzes.size(); i++) {%>
                                                    <tr>
                                                        <td><%=i + 1%></td>
                                                        <td><a class="link-primary" href="<%= request.getContextPath() %>/quiz?quizid=<%= recentQuizzes.get(i).getId() %>">open</a></td>
                                                        <td style="min-width: 180px" ><%= recentQuizzes.get(i).getName() %></td>
                                                        <td><%= recentQuizzes.get(i).getDuration() %></td>
                                                        <td style="min-width: 110px" ><%= recentQuizzes.get(i).getCreationDate() %></td>
                                                        <td style="word-break: break-word;"><%= recentQuizzes.get(i).getDescription() %></td>
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
                <div class="row announcements">
                    <div class="col-12 p-0 card rounded-0">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <div style="margin: 0;">Announcements</div>
                            <%
                            if(user.isAdmin()) {%>
                            <button class="btn btn-outline-secondary p-0 px-2" data-bs-toggle="modal" data-bs-target="#announceModal">Announce</button>
                            <div class="modal fade" id="announceModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="announceModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="announceModalLabel">Announce</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form>
                                                <div class="mb-3">
                                                    <label for="announcementText" class="form-label">Enter Text</label>
                                                    <textarea class="form-control" id="announcementText" rows="3"></textarea>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" id="announceCloseBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="button" id="announceBtn" class="btn btn-primary">Announce</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%}
                            %>
                        </div>
                        <div class="announcements-container-wrapper bg-info">
                            <div class="card-body p-0 m-2 d-flex flex-column announcements-container">
                                <%if(announcements == null || announcements.isEmpty()){%>
                                <p>There are no announcements yet.</p>
                                <%}else{%>
                                <div id="announcementsList" class="announcements-list shadow-lg bg-body rounded" aria-labelledby="announcementsButton">
                                    <div class="list-group announcements-list bg-info">
                                        <%
                                            for (IAnnouncement announcement : announcements) {
                                                SimpleDateFormat dayFormat = new SimpleDateFormat("dd, EE"); // EEEE for full day name
                                                String dayName = dayFormat.format(announcement.getTimeStamp());
                                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                                String time = timeFormat.format(announcement.getTimeStamp());
                                                User announcer = myDb.getUserById(announcement.getUserId());
                                        %>
                                        <div class="announcement-wrapper shadow-sm mb-3 bg-info border-bottom border-info border-2 rounded-top">
                                            <div class="d-flex justify-content-between">
                                                <h6 class="mb-1">By <mark><%=announcer.getUsername()%>:</mark></h6>
                                                <p  class="mb-1"><%=dayName%>, <%=time%></p>
                                            </div>
                                            <div class="announcement-info shadow-sm">
                                                <p><%=announcement.getText()%></p>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js" integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
    <script type="module" src="<%=request.getContextPath()%>/static/scripts/homePage.js"></script>
</body>
</html>
