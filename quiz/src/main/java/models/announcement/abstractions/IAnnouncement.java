package models.announcement.abstractions;

import models.activity.abstractions.IActivity;

import java.util.Date;

public interface IAnnouncement extends IActivity
{
    public int getId();
    public int getUserId();
    public void setUserId(int id);
    public String getText();
    public void setText(String text);
    public Date getTimeStamp();
    public void setTimeStamp(Date date);
}