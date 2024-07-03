package models.announcement.abstractions;

import java.util.Date;

public interface IAnnouncement
{
    public int getId();
    public int getUserId();
    public void setUserId(int id);
    public String getText();
    public void setText(String text);
    public Date getTimeStamp();
    public void setTimeStamp(Date date);
}