package Dao;

import junit.framework.TestCase;
import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import models.USER.User;
import models.announcement.Announcement;
import models.announcement.abstractions.IAnnouncement;
import models.notification.Challenge;
import models.quizes.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class notificationTests extends TestCase {
    private Dao db;
    private BasicDataSource source;

    public notificationTests(){
        source = new BasicDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/quiztestdb");
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);
        db = new mySqlDb(source);
    }

    private void executeSqlFile(String filePath, Connection connection) throws SQLException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            String[] sqlCommands = sql.toString().split(";");
            Statement statement = connection.createStatement();
            for (String command : sqlCommands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command.trim());
                }
            }
        }
    }

    public void setUp() throws SQLException {
        try(Connection con = source.getConnection()){
            executeSqlFile("src/main/java/sql/testDatabase.sql",con);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testGetAnnouncements() throws SQLException {
        ArrayList<IAnnouncement> res = db.getAnnouncements();
        assertEquals(0, res.size());
    }

    public void testRememberAnnouncement() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        db.rememberAnnouncement(new Announcement(1,1,"jaja", new Date()));
        ArrayList<IAnnouncement> res = db.getAnnouncements();
        assertEquals(1, res.size());
        assertEquals("jaja",res.get(0).getText());
    }


    public void testGetUserAnnouncements() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        db.addUser(new User(2,"ja", "password","@", false));
        db.rememberAnnouncement(new Announcement(1,1,"jaja", new Date()));
        db.rememberAnnouncement(new Announcement(2,2,"ja", new Date()));
        ArrayList<IAnnouncement> res = db.getUserAnnouncements(1);
        assertEquals(1, res.size());
        assertEquals("jaja",res.get(0).getText());
    }

    public void testGetUserChallenges() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        db.addUser(new User(2,"ja", "password","@", false));
        assertEquals(0,db.getUserChallenges(1).size());
    }

    public void testSendChallenge() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        db.addUser(new User(2,"ja", "password","@", false));
        db.addQuiz(new Quiz(1,1,"quiz1",new java.sql.Date(System.currentTimeMillis()), "quiz", true, true, 20, false));
        db.sendChallenge(new Challenge(1,1,2,1,new Date()));
        assertEquals(1,db.getUserChallenges(2).size());
        assertEquals(0,db.getUserChallenges(1).size());
    }

    public void testGetSentChallenges() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        db.addUser(new User(2,"ja", "password","@", false));
        db.addQuiz(new Quiz(1,1,"quiz1",new java.sql.Date(System.currentTimeMillis()), "quiz", true, true, 20, false));
        db.sendChallenge(new Challenge(1,1,2,1,new Date()));
        assertEquals(1,db.getSentChallenges(1).size());
        assertEquals(1,db.getUserChallenges(2).size());
    }
    
}
