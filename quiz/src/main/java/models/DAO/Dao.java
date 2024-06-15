package models.DAO;

import models.USER.User;

import java.sql.SQLException;

public interface Dao {
    void closeDbConnection();
    boolean addUser(User user);
    boolean userNameExists(String userName) throws SQLException;
    boolean acountExists(String userName, String passwordHash) throws SQLException;
}