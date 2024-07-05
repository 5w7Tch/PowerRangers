package models.DAO;
import models.quizes.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.quizes.questions.Question;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    void insertIntoQuizHistory(String quizId, String userId, java.sql.Date start, java.sql.Date end, Double score) throws SQLException;
    ArrayList<WritenQuiz> getFriendHistory(Integer quizId, Integer userId) throws SQLException;
    HashSet<Integer> getFriends(Integer userId) throws SQLException;

    void addQuiz(Quiz quiz) throws SQLException;

    void addQuestion(Question question) throws SQLException;


    boolean quizExists(int id) throws SQLException;

    List<Question> getQuestionsByQuizId(int quizId) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    void updateQuiz(Quiz quiz) throws SQLException;

    void deleteQuestions(int quizId) throws SQLException;
}