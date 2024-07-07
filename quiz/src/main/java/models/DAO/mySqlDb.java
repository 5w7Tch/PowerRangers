package models.DAO;

import models.quizes.Quiz;
import models.USER.User;
import models.USER.WritenQuiz;
import models.quizes.questions.Question;
import models.achievement.Achievement;
import models.achievement.abstractions.IAchievement;
import models.enums.AchievementType;
import models.announcement.Announcement;
import models.friend.FriendRequest;
import models.friend.abstractions.IFriendRequest;
import models.notification.Challenge;
import models.notification.Note;
import models.notification.abstractions.IChallenge;
import models.notification.abstractions.INote;
import models.notification.abstractions.INotification;
import org.apache.commons.dbcp2.BasicDataSource;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
                    boolean immediateCorrection = resultSet.getBoolean("immediateCorrection");
                    return new Quiz(id, author, name, creationDate, description, isPracticable, areQuestionsRandom, quizTime,immediateCorrection);
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
                "quizHistory.spentTime, " +
                "quizHistory.userId FROM quizHistory WHERE quizHistory.quizId = ? " +
                "ORDER BY quizHistory.score DESC, quizHistory.spentTime ASC";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<WritenQuiz> writenQuizzes = new ArrayList<>();
                while (resultSet.next()) {
                    double score = resultSet.getDouble("score");
                    Date startTime = resultSet.getDate("startTime");
                    double timeSpent = resultSet.getDouble("spentTime");
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
        deleteQuestions(Integer.parseInt(quizId));

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
    public void addQuiz(Quiz quiz) throws SQLException {
        try(Connection con = dbSource.getConnection()){
            PreparedStatement stm = con.prepareStatement("insert into quizzes" +
                    "(author, name, creationDate, description, isPracticable, areQuestionsRandom, immediateCorrection, quizTime) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1,quiz.getAuthor());
            stm.setString(2,quiz.getName());
            stm.setDate(3,quiz.getCreationDate());
            stm.setString(4,quiz.getDescription());
            stm.setBoolean(5,quiz.isPracticable());
            stm.setBoolean(6,quiz.isQuestionSecRand());
            stm.setBoolean(7,quiz.isImmediateCorrection());
            stm.setDouble(8,quiz.getDuration());
            int rowsAffected = stm.executeUpdate();
            if(rowsAffected==0){
                throw new SQLException();
            }

            try(ResultSet set = stm.getGeneratedKeys()){
                if(set.next()){
                    quiz.setId(set.getInt(1));
                }
            }
        }
    }

    @Override
    public void addQuestion(Question question) throws SQLException {
        try(Connection con = dbSource.getConnection()){
            PreparedStatement stm = con.prepareStatement("insert into questions (quizId, type, questionJson, answerJson, orderNum, score) values (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1,question.getQuizId());
            stm.setString(2,question.getType());
            stm.setString(3,question.getQuestionJson());
            stm.setString(4,question.getAnswerJson());
            stm.setInt(5,question.getOrderNum());
            stm.setDouble(6,question.getScore());
            int rowsAffected = stm.executeUpdate();
            if(rowsAffected==0){
                throw new SQLException();
            }
            try(ResultSet set = stm.getGeneratedKeys()){
                if(set.next()){
                    question.setQuestionId(set.getInt(1));
                }
            }

        }


    }

    @Override
    public boolean quizExists(int id) throws SQLException {
        try(Connection con = dbSource.getConnection()){
            PreparedStatement stm = con.prepareStatement("select * from quizzes where quizId=?");
            stm.setInt(1,id);
            try(ResultSet set = stm.executeQuery()){
                return set.next();
            }
        }
    }

    @Override
    public List<Question> getQuestionsByQuizId(int quizId) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try(Connection con = dbSource.getConnection()){
            PreparedStatement stm = con.prepareStatement("select * from questions where quizId=? order by orderNum");
            stm.setInt(1,quizId);
            try(ResultSet set = stm.executeQuery()){
                List<Question> questions = new ArrayList<>();

                while (set.next()){
                    int questionId = set.getInt("questionId");
                    String type = set.getString("type");
                    String questionJson = set.getString("questionJson");
                    String answerJson = set.getString("answerJson");
                    int orderNum = set.getInt("orderNum");
                    double score = set.getDouble("score");
                    Class<?> questionClass = Question.getClass(type);
                    Question question = (Question) questionClass.getConstructor(int.class,int.class,String.class,String.class,String.class,int.class,double.class)
                            .newInstance(questionId,quizId,type,questionJson,answerJson,orderNum,score);
                    questions.add(question);
                }

                return questions;
            }
        }

    }

    public void updateQuiz(Quiz quiz) throws SQLException {
        try(Connection connection = dbSource.getConnection()){
            PreparedStatement stm  = connection.prepareStatement("update quizzes set name=?, description=?,areQuestionsRandom=?,immediateCorrection=?,isPracticable=?,quizTime=? where quizId=?");
            stm.setString(1,quiz.getName());
            stm.setString(2,quiz.getDescription());
            stm.setBoolean(3,quiz.isQuestionSecRand());
            stm.setBoolean(4,quiz.isImmediateCorrection());
            stm.setBoolean(5,quiz.isPracticable());
            stm.setDouble(6,quiz.getDuration());
            stm.setInt(7,quiz.getId());
            stm.executeUpdate();
        }
    }

    public void deleteQuestions(int quizId) throws SQLException {
        try(Connection connection = dbSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM questions WHERE questions.quizId = ?");
            statement.setInt(1, quizId);
            statement.executeUpdate();
        }
    }

    @Override
    public void insertIntoQuizHistory(String quizId, String userId, java.sql.Date start, Double time, Double score) throws SQLException {
        String query = "insert into quizHistory (quizId, userId, startTime, spentTime, score) values (?, ?, ?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quizId);
            statement.setString(2,userId);
            statement.setDate(3,start);
            statement.setDouble(4,time);
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
                "quizHistory.spentTime, " +
                "quizHistory.userId FROM quizHistory WHERE quizHistory.quizId = ? " +
                "ORDER BY quizHistory.score DESC, quizHistory.spentTime ASC";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double score = resultSet.getDouble("score");
                    Date startTime = resultSet.getDate("startTime");
                    double timeSpent = resultSet.getDouble("spentTime");
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

    @Override
    public boolean addFriendRequest(IFriendRequest friendRequest) throws SQLException {
        String query = "INSERT INTO friendRequests (fromUserId, toUserId, sendTime) VALUES (?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, friendRequest.getFromId());
            statement.setInt(2, friendRequest.getToId());
            statement.setDate(3, (java.sql.Date)friendRequest.getSendTime());
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        }
    }

    @Override
    public boolean friendConnectionExists(Integer user1, Integer user2) throws SQLException {
        String query = "SELECT * FROM friends WHERE (user1Id = ? and user2Id = ?) or (user1Id = ? and user2Id = ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user1);
            statement.setInt(2, user2);
            statement.setInt(3, user2);
            statement.setInt(4, user1);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public boolean sendChallenge(Challenge challenge) throws SQLException {
        String query = "INSERT INTO challenges (fromId, toId, quizId, sendTime) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, challenge.getFromId());
            statement.setInt(2, challenge.getToId());
            statement.setInt(3, challenge.getQuizId());
            statement.setDate(4, (java.sql.Date)challenge.getSendTime());
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        }
    }

    @Override
    public boolean rememberNote(Note note) throws SQLException {
        String query = "INSERT INTO notes (fromId, toId, text, sendTime) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, note.getFromId());
            statement.setInt(2, note.getToId());
            statement.setString(3, note.getText());
            statement.setDate(4, (java.sql.Date)note.getSendTime());
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        }
    }

    @Override
    public boolean rememberAnnouncement(Announcement announcement) throws SQLException {
        String query = "INSERT INTO announcements (userId, text, timeStamp) VALUES (?, ?, ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, announcement.getUserId());
            statement.setString(2, announcement.getText());
            statement.setDate(3, (java.sql.Date)announcement.getTimeStamp());
            boolean rowInserted = statement.executeUpdate() > 0;
            statement.close();
            return rowInserted;
        }
    }


    public ArrayList<IAchievement> getUserAchievements(int userId) throws SQLException {
        String query = "SELECT * FROM achievements a WHERE a.achievementId in (" +
                "select ua.achievementId from userAchievements ua where ua.userId = ?)";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<IAchievement> achievements = new ArrayList<>();
                while (resultSet.next()) {
                    int achievementId = resultSet.getInt("achievementId");
                    String icon = resultSet.getString("icon");
                    AchievementType type = AchievementType.fromOrdinal(resultSet.getInt("type"));
                    String description = resultSet.getString("description");
                    IAchievement achievement = new Achievement(achievementId, icon, type, description);
                    achievements.add(achievement);
                }
                return achievements;
            }
        }
    }

    public ArrayList<WritenQuiz> getUserQuizActivity(int userId) throws SQLException {
        String query = "SELECT quizHistory.score, quizHistory.startTime, " +
                "quizHistory.spentTime, " +
                "quizHistory.quizId FROM quizHistory WHERE quizHistory.userId = ? " +
                "ORDER BY quizHistory.score DESC, spentTime";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<WritenQuiz> writtenQuizzes = new ArrayList<>();
                while (resultSet.next()) {
                    double score = resultSet.getDouble("score");
                    Date startTime = resultSet.getDate("startTime");
                    double timeSpent = resultSet.getDouble("spentTime");
                    int quizId = resultSet.getInt("quizId");
                    String writerName = getUserById(userId).getUsername();
                    WritenQuiz writenQuiz = new WritenQuiz(score, startTime, timeSpent, quizId, userId, writerName);
                    writtenQuizzes.add(writenQuiz);
                }
                return writtenQuizzes;
            }
        }
    }

    public ArrayList<Quiz> getUserCreatedQuizzes(int userId) throws SQLException {
        String query = "SELECT * FROM quizzes WHERE author = ?";
        try (Connection connection = dbSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Quiz> createdQuizzes = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("quizId");
                    int author = resultSet.getInt("author");
                    String name = resultSet.getString("name");
                    Date creationDate = resultSet.getDate("creationDate");
                    String description = resultSet.getString("description");
                    boolean isPracticable = resultSet.getBoolean("isPracticable");
                    boolean areQuestionsRandom = resultSet.getBoolean("areQuestionsRandom");
                    double quizTime = resultSet.getDouble("quizTime");
                    boolean immediateCorrection = resultSet.getBoolean("immediateCorrection");
                    Quiz createdQuiz = new Quiz(id, author, name, creationDate, description, isPracticable, areQuestionsRandom, quizTime,immediateCorrection);
                    createdQuizzes.add(createdQuiz);
                }
                return createdQuizzes;
            }
        }
    }

}
