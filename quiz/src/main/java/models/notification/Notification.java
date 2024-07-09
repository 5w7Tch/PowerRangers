package models.notification;

import models.activity.Activity;
import models.enums.ActivityType;
import models.notification.abstractions.INotification;

import java.util.Date;

public class Notification extends Activity implements INotification
{
    private int toId;

    public Notification(int id, int fromId, int toId, Date sendTime, ActivityType type)
    {
        super(id, fromId, sendTime, type);
        this.toId = toId;
    }

    @Override
    public int getToId()
    {
        return this.toId;
    }

    @Override
    public void setToId(int toId)
    {
        this.toId = toId;
    }
}
