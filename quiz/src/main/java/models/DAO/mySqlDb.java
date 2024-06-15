package models.DAO;

import models.USER.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private int resultSetSize(ResultSet rs) throws SQLException {
        int size = 0;
        while (rs.next()){
            size++;
        }
        return size;
    }

    public boolean userNameExists(String username) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where users.firstName = ?");
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        return resultSetSize(resultSet) == 0;
    }
    public boolean accountExists(String username, String passwordHash) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where users.firstName = ?");
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String columnValue = resultSet.getString("passwordHash");
            return columnValue.equals(passwordHash);
        }
        return false;
    }

    /// is this good practice or not ?????
    public User getUser(String userName, String password) throws SQLException {
        Connection connection = dbSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where users.firstName = ?");
        statement.setString(1 , userName);
        ResultSet resultSet = statement.executeQuery();
        int id = resultSet.getInt("userId");
        String email = resultSet.getString("email");
        boolean isAdmin = resultSet.getBoolean("isAdmin");

        return new User(id , userName , password , email , isAdmin);
    }

}
