package models.DAO;

import models.USER.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.friend.abstractions.IFriendRequest;
import models.notification.abstractions.IChallenge;
import models.notification.abstractions.INote;
import models.notification.abstractions.INotification;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao {
    String DBID = "db";

    void closeDbConnection();
    boolean addUser(User user);
    boolean userNameExists(String userName) throws SQLException;
    boolean accountExists(String userName, String passwordHash) throws SQLException;
    User getUser(String userName, String password) throws SQLException;
    Quiz getQuiz(String id) throws SQLException;
    User getUserById(Integer id) throws SQLException;
    ArrayList<WritenQuiz> getQuizHistory(Integer quizId) throws SQLException;
    void eraseQuiz(String quizId) throws SQLException;
    void clearQuizHistory(String quizId) throws SQLException;
    ArrayList<INotification> getUserNotifications(int userId) throws SQLException;
    ArrayList<INote> getUserNotes(int userId) throws SQLException;
    ArrayList<IChallenge> getUserChallenges(int userId) throws SQLException;
    ArrayList<IFriendRequest> getUserFriendRequests(int userId) throws SQLException;
    boolean acceptFriendRequest(int fromUserId, int toUserId) throws SQLException;
}