package Dao;

import junit.framework.TestCase;
import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import models.USER.User;
import models.achievement.UserAchievement;
import models.achievement.abstractions.IAchievement;
import models.achievement.abstractions.IUserAchievement;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class achievementFuncTests extends TestCase{
    private Dao db;
    private BasicDataSource source;

    public achievementFuncTests(){
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

    public void testGetUserAchievements() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        ArrayList<IAchievement> res = db.getUserAchievements(1);
        assertEquals(0, res.size());
    }

    public void testPutUserAchievements() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        ArrayList<IAchievement> res = db.getUserAchievements(1);
        assertEquals(0, res.size());
        assertEquals(true,db.putUserAchievements(new UserAchievement(1,1,1,new Date())));
    }

    public void testGetAchievementByType() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        int id = db.getAchievementIdFromType(0);
        assertEquals(1,id);
    }

    public void testGetAchievementById() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        IAchievement ach = db.getAchievementById(1);
        assertEquals(1, ach.getAchievementId());
        assertEquals("You have created a quiz.", ach.getDescription());

    }

    public void testGetUserObtainedAchievements() throws SQLException {
        db.addUser(new User(1,"jaja", "password","@", true));
        Date d = new Date();
        db.putUserAchievements(new UserAchievement(1,1,1, d));
        ArrayList<IUserAchievement> res = db.getUserObtainedAchievements(1);
        assertEquals(1, res.size());
        assertEquals(1, res.get(0).getAchievementId());
        assertEquals(1, res.get(0).getUserId());
    }

}
