package achievement;

import junit.framework.TestCase;
import models.achievement.Achievement;
import models.enums.AchievementType;

public class achievementTest extends TestCase {
    public void test1(){
        Achievement achievement = new Achievement(0 , "goodIcon" , AchievementType.AMATEUR_AUTHOR , "amateur");
        assertEquals(achievement.getAchievementId() , 0);
        assertEquals(achievement.getIcon() , "goodIcon");
        assertEquals(achievement.getDescription() , "amateur");
        assertEquals(achievement.getType() , AchievementType.AMATEUR_AUTHOR);
    }

    public void test2(){
        Achievement achievement = new Achievement(0 , "goodIcon" , AchievementType.AMATEUR_AUTHOR , "amateur");
        achievement.setAchievementId(1);
        achievement.setIcon("badIcon");
        achievement.setDescription("best");
        achievement.setType(AchievementType.I_AM_THE_GREATEST);
        assertEquals(achievement.getAchievementId() , 1);
        assertEquals(achievement.getIcon() , "badIcon");
        assertEquals(achievement.getDescription() , "best");
        assertEquals(achievement.getType() , AchievementType.I_AM_THE_GREATEST);

        assertEquals(achievement.toString() , "Achievement{achievementId=1, icon='badIcon', type=I am the Greatest, description='best'}");
    }
}
