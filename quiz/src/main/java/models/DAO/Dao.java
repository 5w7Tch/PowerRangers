package models.DAO;

import models.USER.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.questions.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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
    ArrayList<Question> getQuizQuestions(String quizId) throws SQLException;
    void insertIntoQuizHistory(String quizId, String userId, java.sql.Date start, java.sql.Date end, Double score) throws SQLException;
    ArrayList<WritenQuiz> getFriendHistory(Integer quizId, Integer userId) throws SQLException;
    HashSet<Integer> getFriends(Integer userId) throws SQLException;

}