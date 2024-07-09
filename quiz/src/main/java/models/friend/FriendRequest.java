package models.friend;

import models.enums.ActivityType;
import models.friend.abstractions.IFriendRequest;
import models.notification.Notification;

import java.util.Date;

public class FriendRequest extends Notification implements IFriendRequest
{
    public FriendRequest(int id, int fromUserId, int toUserId, Date sendTime)
    {
        super(id, fromUserId, toUserId, sendTime, ActivityType.FRIEND_REQUEST);
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + super.getId() +
                ", fromId=" + super.getFromId() +
                ", toId=" + super.getToId() + '\'' +
                '}';
    }
}
