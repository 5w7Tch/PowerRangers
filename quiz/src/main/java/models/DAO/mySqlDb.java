package models.DAO;

import models.USER.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class mySqlDb implements Dao{
    private final BasicDataSource dbSource;

    public mySqlDb(BasicDataSource source){
        dbSource = source;
    }


    @Override
    public void closeDbConnection() {
        try {
            dbSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUser(User user) {
        try {
            Connection connection = dbSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into users(firstName,email,passwordHash,isAdmin)" +
                    "values (?,?,?,?)");
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getPasswordHash());
            statement.setBoolean(4,user.isAdmin());
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
