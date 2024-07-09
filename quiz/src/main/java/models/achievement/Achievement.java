package models.achievement;

import models.achievement.abstractions.IAchievement;
import models.enums.AchievementType;

import java.util.Date;

public class Achievement implements IAchievement
{
    private int achievementId;
    private String icon;
    private AchievementType type;
    private String description;

    public Achievement(int id, String icon, AchievementType type, String description)
    {
        this.achievementId = id;
        this.icon = icon;
        this.type = type;
        this.description = description;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "achievementId=" + achievementId +
                ", icon='" + icon + '\'' +
                ", type=" + type.getDisplayName() +
                ", description='" + description + '\'' +
                '}';
    }
}
