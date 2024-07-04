package models.achievement.abstractions;

import models.enums.AchievementType;

import java.util.Date;

public interface IAchievement
{
    int getAchievementId();
    void setAchievementId(int id);
    String getName();
    void setName(String name);
    String getIcon();
    void setIcon(String icon);
    AchievementType getType();
    void setType(AchievementType type);
    String getDescription();
    void setDescription(String description);
}