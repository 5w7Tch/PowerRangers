package models.friend;

import models.friend.abstractions.IFriendRequest;

public class FriendRequest implements IFriendRequest
{
    private int id;
    private int fromUserId;
    private int toUserId;

    public FriendRequest(int id, int fromUserId, int toUserId)
    {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
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
    public int getFromUserId()
    {
        return this.fromUserId;
    }

    @Override
    public void setFromUserId(int id)
    {
        this.fromUserId = id;
    }

    @Override
    public int getToUserId()
    {
        return this.toUserId;
    }

    @Override
    public void setToUserId(int id)
    {
        this.toUserId = id;
    }
}
