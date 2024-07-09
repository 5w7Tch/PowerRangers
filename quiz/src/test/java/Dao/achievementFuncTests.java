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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class achievementFuncTests extends TestCase{
    private Dao db;
    private BasicDataSource source;
    @Override
    protected void setUp() throws Exception {
        source = new BasicDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/quiztestdb");
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);

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
        String query = "drop table achievements if exists";
        source.getConnection().prepareStatement(query).execute();
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
        ArrayList<IUserAchievement> res = db.getUserObtainedAchievements(1);
        assertEquals(1, res.size());
        String query = "drop table achievements if exists";
        source.getConnection().prepareStatement(query).execute();
        res = db.getUserObtainedAchievements(1);
        assertEquals(0, res.size());
    }
}
