package Dao;

import junit.framework.TestCase;
import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import org.apache.commons.dbcp2.BasicDataSource;


public class achievementFuncTests extends TestCase{
    private Dao db;
    private BasicDataSource source;
    @Override
    protected void setUp() throws Exception {
        source = new BasicDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/quiztestdb");
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);
        db = new mySqlDb(source);
    }

    public void testGetUserAchievements(){

    }

    public void testPutUserAchievements(){

    }

    public void testGetAchievementByType(){

    }

    public void testGetAchievementById(){

    }

    public void testGetUserObtainedAchievements(){

    }
}
