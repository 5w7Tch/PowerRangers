package models.friend.abstractions;

import models.activity.abstractions.IActivity;

import java.util.Date;

public interface IFriend extends IActivity
{
    public int getId();
    public void setId(int id);
    public int getUserOneId();
    public void setUserOneId(int id);
    public int getUserTwoId();
    public void setUserTwoId(int id);
    public Date getTimeStamp();
    public void setTimeStamp(Date date);
}