package models.activity;

import models.activity.abstractions.IActivity;
import models.enums.ActivityType;

import java.util.Date;

public class Activity implements IActivity
{
    private int id;
    private int fromId;
    private ActivityType type;
    private Date sendTime;

    public Activity(int id, int fromId, Date sendTime, ActivityType type)
    {
        this.id = id;
        this.fromId = fromId;
        this.sendTime = sendTime;
        this.type = type;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public int getFromId()
    {
        return this.fromId;
    }

    @Override
    public void setFromId(int fromId)
    {
        this.fromId = fromId;
    }
    @Override
    public ActivityType getType()
    {
        return this.type;
    }

    @Override
    public void setType(ActivityType type)
    {
        this.type = type;
    }

    @Override
    public Date getSendTime()
    {
        return this.sendTime;
    }

    @Override
    public void setSendTime(Date time)
    {
        this.sendTime = time;
    }
}
