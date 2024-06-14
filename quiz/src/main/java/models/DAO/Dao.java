package models.DAO;

import models.USER.User;

public interface Dao {
    void closeDbConnection();
    boolean addUser(User user);
}