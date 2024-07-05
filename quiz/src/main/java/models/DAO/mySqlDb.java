package models.DAO;

import models.USER.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.friend.FriendRequest;
import models.friend.abstractions.IFriendRequest;
import models.notification.Challenge;
import models.notification.Note;
import models.notification.abstractions.IChallenge;
import models.notification.abstractions.INote;
import models.notification.abstractions.INotification;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
                    return new Quiz(id, author, name, creationDate, description, isPracticable, areQuestionsRandom, quizTime);
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
    public ArrayList<INotification> getUserNotifications(int userId) throws SQLException
    {
        User user = getUserById(userId);
        if(user == null)
            throw new RuntimeException("User can't be null");
        ArrayList<INotification> notifications = new ArrayList<>();
        ArrayList<INote> notes = getUserNotes(userId);
        ArrayList<IChallenge> challenges = getUserChallenges(userId);
        ArrayList<IFriendRequest> friendRequests = getUserFriendRequests(userId);
        notifications.addAll(notes);
        notifications.addAll(challenges);
        notifications.addAll(friendRequests);

        notifications.sort((n1, n2) -> n2.getSendTime().compareTo(n1.getSendTime()));

        return notifications;
    }

    @Override
    public ArrayList<INote> getUserNotes(int userId) throws SQLException
    {
        String query = "SELECT * FROM notes WHERE notes.toId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<INote> notes = new ArrayList<>();
                while (resultSet.next()) {
                    int noteId = resultSet.getInt("noteId");
                    int fromId = resultSet.getInt("fromId");
                    int toId = resultSet.getInt("toId");
                    String text = resultSet.getString("text");
                    Date sendTime = resultSet.getDate("sendTime");
                    INote note = new Note(noteId, fromId, toId, text, sendTime);
                    notes.add(note);
                }
                return notes;
            }
        }
    }

    @Override
    public ArrayList<IChallenge> getUserChallenges(int userId) throws SQLException
    {
        String query = "SELECT * FROM challenges WHERE challenges.toId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<IChallenge> challenges = new ArrayList<>();
                while (resultSet.next()) {
                    int challengeId = resultSet.getInt("challengeId");
                    int fromId = resultSet.getInt("fromId");
                    int toId = resultSet.getInt("toId");
                    int quizId = resultSet.getInt("quizId");
                    Date sendTime = resultSet.getDate("sendTime");
                    IChallenge challenge = new Challenge(challengeId, fromId, toId, quizId, sendTime);
                    challenges.add(challenge);
                }
                return challenges;
            }
        }
    }

    @Override
    public ArrayList<IFriendRequest> getUserFriendRequests(int userId) throws SQLException
    {
        String query = "SELECT * FROM friendRequests WHERE friendRequests.toUserId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<IFriendRequest> friendRequests = new ArrayList<>();
                while (resultSet.next()) {
                    int requestId = resultSet.getInt("requestId");
                    int fromUserId = resultSet.getInt("fromUserId");
                    int toUserId = resultSet.getInt("toUserId");
                    Date sendTime = resultSet.getDate("sendTime");
                    IFriendRequest friendRequest = new FriendRequest(requestId, fromUserId, toUserId, sendTime);
                    friendRequests.add(friendRequest);
                }
                return friendRequests;
            }
        }
    }

    @Override
    public IFriendRequest getFriendRequestById(int friendRequestId) throws SQLException{
        String query = "SELECT * FROM friendRequests WHERE friendRequests.requestId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, friendRequestId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int requestId = resultSet.getInt("requestId");
                    int fromUserId = resultSet.getInt("fromUserId");
                    int toUserId = resultSet.getInt("toUserId");
                    Date sendTime = resultSet.getDate("sendTime");
                    return new FriendRequest(requestId, fromUserId, toUserId, sendTime);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public boolean addFriend(IFriendRequest friendRequest) throws SQLException {
        String query = "INSERT INTO friends (user1Id, user2Id) VALUES (?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, friendRequest.getFromId());
            statement.setInt(2, friendRequest.getToId());
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        }
    }

    @Override
    public boolean acceptFriendRequest(IFriendRequest friendRequest) throws SQLException{
        return addFriend(friendRequest) && removeFriendRequest(friendRequest);
    }

    @Override
    public boolean removeFriendRequest(IFriendRequest friendRequest) throws SQLException{
        String query = "DELETE FROM friendRequests WHERE requestId = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, friendRequest.getId());
            boolean rowDeleted = statement.executeUpdate() > 0;
            statement.close();
            return rowDeleted;
        }
    }

}
