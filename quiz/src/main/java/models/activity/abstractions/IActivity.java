package models.activity.abstractions;

import models.enums.ActivityType;

import java.util.Date;

public interface IActivity
{
    int getId();
    void setId(int id);

    int getFromId();
    void setFromId(int fromId);

    ActivityType getType();
    void setType(ActivityType type);

    Date getSendTime();
    void setSendTime(Date time);
}
