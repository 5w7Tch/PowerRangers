package models.achievement;

import models.achievement.abstractions.IUserAchievement;

import java.util.Date;

public class UserAchievement implements IUserAchievement
{
    private int userAchievementId;
    private int userId;
    private int achievementId;
    private Date timeStamp;

    public UserAchievement(int userAchievementId, int userId, int achievementId, Date timeStamp) {
        this.userAchievementId = userAchievementId;
        this.userId = userId;
        this.achievementId = achievementId;
        this.timeStamp = timeStamp;
    }

    public int getUserAchievementId() {
        return userAchievementId;
    }

    public void setUserAchievementId(int userAchievementId) {
        this.userAchievementId = userAchievementId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "UserAchievement{" +
                "userAchievementId=" + userAchievementId +
                ", userId=" + userId +
                ", achievementId=" + achievementId +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
