package models.DAO;

import models.USER.User;

import java.sql.SQLException;

public interface Dao {
    String DBID = "db";
    void closeDbConnection();
    boolean addUser(User user);
    boolean userNameExists(String userName) throws SQLException;
    boolean accountExists(String userName, String passwordHash) throws SQLException;
    User getUser(String userName, String password) throws SQLException;
}