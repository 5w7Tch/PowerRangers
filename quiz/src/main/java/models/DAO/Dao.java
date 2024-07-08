package models.DAO;
import models.achievement.UserAchievement;
import models.achievement.abstractions.IUserAchievement;
import models.activity.abstractions.IActivity;
import models.announcement.abstractions.IAnnouncement;
import models.friend.abstractions.IFriend;
import models.quizes.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.quizes.questions.Question;
import models.announcement.Announcement;
import models.achievement.abstractions.IAchievement;
import models.friend.abstractions.IFriendRequest;
import models.notification.Challenge;
import models.notification.Note;
import models.notification.abstractions.IChallenge;
import models.notification.abstractions.INote;
import models.notification.abstractions.INotification;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface Dao {
    String DBID = "db";

    void closeDbConnection();
    boolean addUser(User user);
    boolean deleteUser(int id) throws SQLException;
    boolean userNameExists(String userName) throws SQLException;
    boolean accountExists(String userName, String passwordHash) throws SQLException;
    User getUser(String userName, String password) throws SQLException;
    Quiz getQuiz(String id) throws SQLException;
    User getUserById(Integer id) throws SQLException;
    ArrayList<WritenQuiz> getQuizHistory(Integer quizId) throws SQLException;
    void eraseQuiz(String quizId) throws SQLException;
    void clearQuizHistory(String quizId) throws SQLException;
    void insertIntoQuizHistory(String quizId, String userId, java.sql.Date start, Double time, Double score) throws SQLException;
    ArrayList<WritenQuiz> getFriendHistory(Integer quizId, Integer userId) throws SQLException;
    HashSet<Integer> getFriends(Integer userId) throws SQLException;

    void addQuiz(Quiz quiz) throws SQLException;

    void addQuestion(Question question) throws SQLException;


    boolean quizExists(int id) throws SQLException;

    List<Question> getQuestionsByQuizId(int quizId) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    void updateQuiz(Quiz quiz) throws SQLException;

    void deleteQuestions(int quizId) throws SQLException;
    ArrayList<INotification> getUserNotifications(int userId) throws SQLException;
    ArrayList<INote> getUserNotes(int userId) throws SQLException;
    ArrayList<IChallenge> getUserChallenges(int userId) throws SQLException;
    ArrayList<IFriendRequest> getUserFriendRequests(int userId) throws SQLException;
    IFriendRequest getFriendRequestById(int friendRequestId) throws SQLException;
    boolean addFriend(IFriendRequest friendRequest) throws SQLException;
    boolean acceptFriendRequest(IFriendRequest friendRequest) throws SQLException;
    boolean removeFriendRequest(IFriendRequest friendRequest) throws SQLException;
    boolean addFriendRequest(IFriendRequest friendRequest) throws SQLException;
    boolean friendConnectionExists(Integer user1, Integer user2) throws SQLException;
    boolean sendChallenge(Challenge challenge) throws SQLException;
    boolean rememberNote(Note note) throws SQLException;
    boolean rememberAnnouncement(Announcement announcement) throws SQLException;
    IAchievement getAchievementById(int achievementId) throws SQLException;
    ArrayList<IAchievement> getUserAchievements(int userId) throws SQLException;
    ArrayList<WritenQuiz> getUserQuizActivity(int userId) throws SQLException;
    ArrayList<Quiz> getUserCreatedQuizzes(int userId) throws SQLException;
    ArrayList<Quiz> getPopularQuizzes() throws SQLException;
    ArrayList<Quiz> getRecentQuizzes() throws SQLException;
    ArrayList<IAnnouncement> getAnnouncements() throws SQLException;
    Integer getUserByName(String userName) throws SQLException;
    ArrayList<IAnnouncement> getUserAnnouncements(int userId) throws SQLException;
    ArrayList<IUserAchievement> getUserObtainedAchievements(int userId) throws SQLException;
    ArrayList<IFriend> getUserFriends(int userId) throws SQLException;
    ArrayList<IChallenge> getSentChallenges(int userId) throws SQLException;
    ArrayList<WritenQuiz> getUserWrittenQuizzes(int userId) throws SQLException;
    ArrayList<IActivity> getFriendsActivity(int userId) throws SQLException;

    boolean promoteUser(Integer user_id) throws SQLException;

    boolean friendRequestExists(Integer user1Id, Integer user2Id) throws SQLException;
}