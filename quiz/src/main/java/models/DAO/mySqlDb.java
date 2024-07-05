package models.DAO;

import models.USER.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.questions.Question;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class mySqlDb implements Dao {
    private final BasicDataSource dbSource;

    public mySqlDb(BasicDataSource source) {
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
        String query = "INSERT INTO users (firstName, email, passwordHash, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setBoolean(4, user.isAdmin());
            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    user.setId(set.getInt(1)); // Use column index instead of column name
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userNameExists(String username) throws SQLException {
        String query = "SELECT 1 FROM users WHERE firstName = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean accountExists(String username, String passwordHash) throws SQLException {
        String query = "SELECT 1 FROM users WHERE firstName = ? AND passwordHash = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public User getUser(String userName, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE firstName = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("userId");
                    String email = resultSet.getString("email");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    return new User(id, userName, password, email, isAdmin);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Quiz getQuiz(String quizId) throws SQLException {
        String query = "SELECT * FROM quizzes WHERE quizId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("quizId");
                    int author = resultSet.getInt("author");
                    String name = resultSet.getString("name");
                    Date creationDate = resultSet.getDate("creationDate");
                    String description = resultSet.getString("description");
                    boolean isPracticable = resultSet.getBoolean("isPracticable");
                    boolean areQuestionsRandom = resultSet.getBoolean("areQuestionsRandom");
                    double quizTime = resultSet.getDouble("quizTime");
                    boolean immediateCorrection = resultSet.getBoolean("immediateCorrection");
                    return new Quiz(id, author, name, creationDate, description, isPracticable, areQuestionsRandom, quizTime, immediateCorrection);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public User getUserById(Integer userId) throws SQLException {
        String query = "SELECT * FROM users WHERE userId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String userName = resultSet.getString("firstName");
                    String password = resultSet.getString("passwordHash");
                    String email = resultSet.getString("email");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");
                    return new User(userId, userName, password, email, isAdmin);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public ArrayList<WritenQuiz> getQuizHistory(Integer quizId) throws SQLException {

        String query = "SELECT quizHistory.score, quizHistory.startTime, " +
                "TIMESTAMPDIFF(MINUTE, quizHistory.startTime, quizHistory.endTime) AS timeSpent, " +
                "quizHistory.userId FROM quizHistory WHERE quizHistory.quizId = ? " +
                "ORDER BY quizHistory.score DESC, timeSpent ASC";

        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<WritenQuiz> writenQuizzes = new ArrayList<>();
                while (resultSet.next()) {
                    double score = resultSet.getDouble("score");
                    Date startTime = resultSet.getDate("startTime");
                    double timeSpent = resultSet.getDouble("timeSpent");
                    int userId = resultSet.getInt("userId");
                    String writerName = getUserById(userId).getUsername();
                    WritenQuiz writenQuiz = new WritenQuiz(score, startTime, timeSpent, quizId, userId, writerName);
                    writenQuizzes.add(writenQuiz);
                }
                return writenQuizzes;
            }
        }
    }

    @Override
    public void eraseQuiz(String quizId) throws SQLException {
        //delete from challenges
        String query = "DELETE FROM challenges WHERE challenges.quizId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //delete from questions
        query = "DELETE FROM questions WHERE questions.quizId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //delete from history
        clearQuizHistory(quizId);
        //erase quiz
        query = "DELETE FROM quizzes WHERE quizzes.quizId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void clearQuizHistory(String quizId) throws SQLException {
        String query = "DELETE FROM quizHistory WHERE quizHistory.quizId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Question> getQuizQuestions(String quizId) throws SQLException {
        ArrayList<Question> res = new ArrayList<>();

        return res;
    }

    @Override
    public void insertIntoQuizHistory(String quizId, String userId, java.sql.Date start, java.sql.Date end, Double score) throws SQLException {
        String query = "insert into quizHistory (quizId, userId, startTime, endTime, score) values (?, ?, ?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            statement.setString(2,userId);
            statement.setDate(3,start);
            statement.setDate(4,end);
            statement.setDouble(5,score);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public ArrayList<WritenQuiz> getFriendHistory(Integer quizId, Integer userId) throws SQLException {

        HashSet<Integer> friends = getFriends(userId);
        ArrayList<WritenQuiz> writenQuizzes = new ArrayList<>();

        String query = "SELECT quizHistory.score, quizHistory.startTime, " +
                "TIMESTAMPDIFF(MINUTE, quizHistory.startTime, quizHistory.endTime) AS timeSpent, " +
                "quizHistory.userId FROM quizHistory WHERE quizHistory.quizId = ? " +
                "ORDER BY quizHistory.score DESC, timeSpent ASC";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double score = resultSet.getDouble("score");
                    Date startTime = resultSet.getDate("startTime");
                    double timeSpent = resultSet.getDouble("timeSpent");
                    Integer user_Id = resultSet.getInt("userId");
                    String writerName = getUserById(userId).getUsername();
                    if(friends.contains(user_Id)){
                        WritenQuiz writenQuiz = new WritenQuiz(score, startTime, timeSpent, quizId, user_Id, writerName);
                        writenQuizzes.add(writenQuiz);
                    }
                }
            }
        }
        return writenQuizzes;
    }

    @Override
    public HashSet<Integer> getFriends(Integer userId) throws SQLException {
        String query1 = "SELECT friends.user1Id, friends.user2Id FROM friends  WHERE friends.user1Id = ? or friends.user2Id = ?";

        HashSet<Integer> friends = new HashSet<>();
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer user1Id = resultSet.getInt("user1Id");
                    Integer user2Id = resultSet.getInt("user2Id");
                    if(user2Id.equals(userId)){
                        friends.add(user1Id);
                    }else if(user1Id.equals(userId)){
                        friends.add(user2Id);
                    }
                }
            }
        }
        return friends;
    }

}
