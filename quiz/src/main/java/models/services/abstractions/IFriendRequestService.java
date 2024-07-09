package models.services.abstractions;

import models.friend.abstractions.IFriendRequest;

import java.util.List;

public interface IFriendRequestService
{
    public void sendFriendRequest(int fromUserId, int toUserId);

    public void acceptFriendRequest(int requestId);

    public void rejectFriendRequest(int requestId);

    public List<IFriendRequest> getFriendRequests(int userId);

    public void cancelFriendRequest(int requestId);
}
