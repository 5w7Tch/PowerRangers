package models.achievement.abstractions;

import models.activity.abstractions.IActivity;

import java.util.Date;

public interface IUserAchievement extends IActivity
{
    int getUserAchievementId();
    void setUserAchievementId(int userAchievementId);

    int getUserId();
    void setUserId(int userId);

    int getAchievementId();
    void setAchievementId(int achievementId);

    Date getTimeStamp();
    void setTimeStamp(Date timeStamp);
}
