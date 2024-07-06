package models.notification.abstractions;

import models.enums.NotificationType;

import java.util.Date;

public interface INotification
{
    int getId();
    void setId(int id);

    int getFromId();
    void setFromId(int fromId);

    int getToId();
    void setToId(int toId);

    NotificationType getType();
    void setType(NotificationType type);

    Date getSendTime();
    void setSendTime(Date time);
}
