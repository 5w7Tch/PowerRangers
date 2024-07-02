package models.DAO;

import models.quizes.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;

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
}