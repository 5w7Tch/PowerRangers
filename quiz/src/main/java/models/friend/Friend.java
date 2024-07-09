package models.friend;

import models.activity.Activity;
import models.enums.ActivityType;
import models.friend.abstractions.IFriend;

import java.util.Date;

public class Friend extends Activity implements IFriend
{
    private int userTwoId;

    public Friend(int id, int userOneId, int userTwoId, Date timeStamp) {
        super(id, userOneId, timeStamp, ActivityType.FRIENDSHIP);
        this.userTwoId = userTwoId;
    }

    @Override
    public int getId()
    {
        return super.getId();
    }

    @Override
    public void setId(int id)
    {
        super.setId(id);
    }

    @Override
    public int getUserOneId()
    {
        return super.getFromId();
    }

    @Override
    public void setUserOneId(int id)
    {
        super.setFromId(id);
    }

    @Override
    public int getUserTwoId()
    {
        return this.userTwoId;
    }

    @Override
    public void setUserTwoId(int id)
    {
        this.userTwoId = id;
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
