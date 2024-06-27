package services;

import models.DAO.mySqlDb;
import models.USER.User;
import services.abstractions.IFriendService;

import java.util.List;

public class FriendService implements IFriendService
{
    private mySqlDb db;
    public FriendService(mySqlDb db)
    {
        this.db = db;
    }

    @Override
    public void addFriend(int userOneId, int userTwoId)
    {

    }

    @Override
    public void removeFriend(int userOneId, int userTwoId)
    {

    }

    @Override
    public List<User> getFriends(int userId)
    {
        return null;
    }

    @Override
    public boolean areFriends(int userOneId, int userTwoId)
    {
        return false;
    }
}
