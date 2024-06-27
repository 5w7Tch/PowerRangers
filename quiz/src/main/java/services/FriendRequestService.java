package services;

import models.DAO.mySqlDb;
import models.friend.abstractions.IFriendRequest;
import services.abstractions.IFriendRequestService;

import java.util.List;

public class FriendRequestService implements IFriendRequestService
{
    private mySqlDb db;
    public FriendRequestService(mySqlDb db)
    {
        this.db = db;
    }

    @Override
    public void sendFriendRequest(int fromUserId, int toUserId)
    {

    }

    @Override
    public void acceptFriendRequest(int requestId)
    {

    }

    @Override
    public void rejectFriendRequest(int requestId)
    {

    }

    @Override
    public List<IFriendRequest> getFriendRequests(int userId)
    {
        return null;
    }

    @Override
    public void cancelFriendRequest(int requestId)
    {

    }
}
