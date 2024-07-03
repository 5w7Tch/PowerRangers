package models.services.abstractions;

import models.USER.User;

import java.util.List;

public interface IFriendService
{
    public void addFriend(int userOneId, int userTwoId);
    public void removeFriend(int userOneId, int userTwoId);
    public List<User> getFriends(int userId);
    public boolean areFriends(int userOneId, int userTwoId);
}
