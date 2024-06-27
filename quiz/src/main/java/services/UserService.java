package services;

import models.DAO.mySqlDb;
import models.USER.User;
import services.abstractions.IUserService;

public class UserService implements IUserService
{
    private mySqlDb db;

    public UserService(mySqlDb db)
    {
        this.db = db;
    }

    @Override
    public void addUser(User user)
    {

    }

    @Override
    public User getUserById(int userId)
    {
        return null;
    }

    @Override
    public void updateUser(User user)
    {

    }

    @Override
    public void deleteUser(int userId)
    {

    }
}
