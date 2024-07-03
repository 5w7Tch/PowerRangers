package models.achievement.abstractions;

import java.util.Date;

public interface IUserAchievement
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
