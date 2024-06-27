package models.services.abstractions;

import models.USER.User;

public interface IUserService
{
    public void addUser(User user);
    public User getUserById(int userId);
    public void updateUser(User user);
    public void deleteUser(int userId) ;
}
