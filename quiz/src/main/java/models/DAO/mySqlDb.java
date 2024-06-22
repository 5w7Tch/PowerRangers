package models.DAO;

import models.USER.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class mySqlDb implements Dao{
    private final BasicDataSource dbSource;

    public mySqlDb(BasicDataSource source){
        dbSource = source;
    }


    @Override
    public void closeDbConnection() {
        try {
            dbSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUser(User user) {
        try {
            Connection connection = dbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into users(firstName,email,passwordHash,isAdmin)" +
                    "values (?,?,?,?)");
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getPasswordHash());
            statement.setBoolean(4,user.isAdmin());
            statement.execute();
            ResultSet set = statement.getGeneratedKeys();
            if(set.next()){
                user.setId(set.getInt("userId"));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean userNameExists(String username) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where users.firstName = ?");
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    public boolean accountExists(String username, String passwordHash) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where firstName = ? and passwordHash = ?");
        statement.setString(1,username);
        statement.setString(2,passwordHash);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    /// is this good practice or not ?????
    public User getUser(String userName, String password) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where users.firstName = ?");
        statement.setString(1 , userName);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("userId");
        String email = resultSet.getString("email");
        boolean isAdmin = resultSet.getBoolean("isAdmin");

        return new User(id , userName , password , email , isAdmin);
    }

    @Override
    public Quiz getQuiz(String quizId) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from quizzes where quizId = ?");
        statement.setString(1 , quizId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int id = resultSet.getInt("quizId");
        int author = resultSet.getInt("author");
        String name = resultSet.getString("name");
        Date creationDate = resultSet.getDate("creationDate");
        String deck = resultSet.getString("description");
        boolean isPracticable = resultSet.getBoolean("isPracticable");
        Double duration = resultSet.getDouble("quizTime");
        return new Quiz(id, author , name , creationDate , deck , isPracticable , duration);
    }

    @Override
    public User getUserById(Integer userId) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where users.userId = ?");
        statement.setString(1 , userId.toString());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String userName = resultSet.getString("firstName");
        String password = resultSet.getString("passwordHash");
        int id = resultSet.getInt("userId");
        String email = resultSet.getString("email");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        return new User(id , userName , password , email , isAdmin);
    }

    @Override
    public ArrayList<WritenQuiz> getQuizHistory(Integer quizId) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select quizHistory.score, quizHistory.startTime, TIMESTAMPDIFF(MINUTE, quizHistory.startTime, quizHistory.endTime) AS time_Spent, quizHistory.userId from quizHistory where quizHistory.quizId = ? order by quizHistory.score desc, TIMESTAMPDIFF(MINUTE, quizHistory.endTime, quizHistory.startTime) asc");
        statement.setString(1 , quizId.toString());
        ResultSet resultSet = statement.executeQuery();
        ArrayList<WritenQuiz> writenQuizzes = new ArrayList<>();
        while(resultSet.next()){
            Double score = resultSet.getDouble("score");
            Date start = resultSet.getDate("startTime");
            Double time = resultSet.getDouble("time_Spent");
            int id = resultSet.getInt("userId");
            WritenQuiz quiz = new WritenQuiz(score,start, time, quizId, id, getUserById(id).getUsername());
            writenQuizzes.add(quiz);
        }
        return writenQuizzes;
    }

}
