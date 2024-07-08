package models.announcement;

import models.activity.Activity;
import models.announcement.abstractions.IAnnouncement;
import models.enums.ActivityType;

import java.util.Date;

public class Announcement extends Activity implements IAnnouncement
{
    private String text;

    public Announcement(int id, int userId, String text, Date timeStamp)
    {
        super(id, userId, timeStamp, ActivityType.ANNOUNCEMENT);
        this.text = text;
    }

    @Override
    public int getUserId()
    {
        return super.getFromId();
    }

    @Override
    public void setUserId(int id)
    {
        super.setFromId(id);
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
        return super.getSendTime();
    }

    @Override
    public void setTimeStamp(Date date)
    {
        super.setSendTime(date);
    }
}
