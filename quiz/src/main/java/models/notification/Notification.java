package models.notification;

import models.enums.NotificationType;
import models.notification.abstractions.INotification;

import java.util.Date;

public class Notification implements INotification
{
    private int id;
    private int fromId;
    private int toId;
    private NotificationType type;
    private Date sendTime;

    public Notification(int id, int fromId, int toId, Date sendTime, NotificationType type)
    {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
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
    public int getToId()
    {
        return this.toId;
    }

    @Override
    public void setToId(int toId)
    {
        this.toId = toId;
    }

    @Override
    public NotificationType getType()
    {
        return this.type;
    }

    @Override
    public void setType(NotificationType type)
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
