package models.friend;

import models.friend.abstractions.IFriend;

public class Friend implements IFriend
{
    private int id;
    private int userOneId;
    private int userTwoId;

    public Friend(int id, int userOneId, int userTwoId)
    {
        this.id = id;
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
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
    public int getUserOneId()
    {
        return this.userOneId;
    }

    @Override
    public void setUserOneId(int id)
    {
        this.userOneId = id;
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
}
