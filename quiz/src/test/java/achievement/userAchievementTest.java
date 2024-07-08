package achievement;

import junit.framework.TestCase;
import models.achievement.UserAchievement;

import java.time.Instant;
import java.util.Date;

public class userAchievementTest extends TestCase {
    public void test1(){
        Date  date = Date.from(Instant.now());
        UserAchievement achievement = new UserAchievement(0 , 0 , 0 , date);
        assertEquals(achievement.getAchievementId() , 0);
        assertEquals(achievement.getUserAchievementId() , 0);
        assertEquals(achievement.getUserId() , 0);
        assertEquals(achievement.getTimeStamp() , date);
    }

    public void test2(){
        Date  date = Date.from(Instant.now());
        UserAchievement achievement = new UserAchievement(0 , 0 , 0 , date);
        achievement.setAchievementId(1);
        achievement.setUserAchievementId(1);
        achievement.setUserId(1);
        Date newDate = Date.from(Instant.now());
        achievement.setTimeStamp(newDate);
        assertEquals(achievement.getAchievementId() , 1);
        assertEquals(achievement.getUserAchievementId() , 1);
        assertEquals(achievement.getUserId() , 1);
        assertEquals(achievement.getTimeStamp() , newDate);
        assertEquals(achievement.toString() , "UserAchievement{userAchievementId=1, userId=1, achievementId=1, timeStamp="+newDate+"}");
    }
}
