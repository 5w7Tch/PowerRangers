package models.achievement;

import models.achievement.abstractions.IUserAchievement;
import models.activity.Activity;
import models.enums.ActivityType;

import java.util.Date;

public class UserAchievement extends Activity implements IUserAchievement
{
    private int achievementId;

    public UserAchievement(int userAchievementId, int userId, int achievementId, Date timeStamp) {
        super(userAchievementId, userId, timeStamp, ActivityType.ACHIEVEMENT);
        this.achievementId = achievementId;
    }

    public int getUserAchievementId() {
        return super.getId();
    }

    public void setUserAchievementId(int userAchievementId) {
        super.setId(userAchievementId);
    }

    public int getUserId() {
        return super.getFromId();
    }

    public void setUserId(int userId) {
        super.setFromId(userId);
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public Date getTimeStamp() {
        return super.getSendTime();
    }

    public void setTimeStamp(Date timeStamp) {
        super.setSendTime(timeStamp);
    }

    @Override
    public String toString() {
        return "UserAchievement{" +
                "userAchievementId=" + super.getId() +
                ", userId=" + super.getFromId() +
                ", achievementId=" + achievementId +
                ", timeStamp=" + super.getSendTime() +
                '}';
    }
}
