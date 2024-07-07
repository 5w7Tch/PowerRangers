package models.notification.abstractions;

import models.activity.abstractions.IActivity;

public interface INotification extends IActivity
{
    int getToId();
    void setToId(int toId);
}
