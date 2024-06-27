package models.friend.abstractions;

public interface IFriendRequest
{
    public int getId();
    public void setId(int id);
    public int getFromUserId();
    public void setFromUserId(int id);
    public int getToUserId();
    public void setToUserId(int id);
}
