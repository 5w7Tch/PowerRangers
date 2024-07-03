package models.announcement;

import models.announcement.abstractions.IAnnouncement;

import java.util.Date;

public class Announcement implements IAnnouncement
{
    private final int id;
    private int userId;
    private String text;
    private Date timeStamp;

    public Announcement(int id, int userId, String text, Date timeStamp)
    {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public int getUserId()
    {
        return this.userId;
    }

    @Override
    public void setUserId(int id)
    {
        this.userId = id;
    }

    @Override
    public String getText()
    {
        return this.text;
    }

    @Override
    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public Date getTimeStamp()
    {
        return this.timeStamp;
    }

    @Override
    public void setTimeStamp(Date date)
    {
        this.timeStamp = date;
    }
}
