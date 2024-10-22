package Dao;

import junit.framework.TestCase;
import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import models.USER.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFunctionsTest extends TestCase {
    private Dao db;
    private BasicDataSource source;
    public UserFunctionsTest(){
        source = new BasicDataSource();
        source.setUrl(dbCredentials.url);
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);
        db = new mySqlDb(source);
    }

    public void testAddUser() throws SQLException {
        User u = new User(-1,"quji","bla","bla@gmail.com",true);
        int iD = db.getUserByName("quji");
        if(iD!=-1){
            db.deleteUser(iD);
        }

        db.addUser(u);

        //test user exists
        try(Connection con = source.getConnection()){
            PreparedStatement stm = con.prepareStatement("select * from users where firstName=?");
            stm.setString(1,"quji");
            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString("firstName");
                String passwordHash = resultSet.getString("passwordHash");
                String email = resultSet.getString("email");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                User u1 = new User(id,name,passwordHash,email,isAdmin);
                u1.setHash(passwordHash);
                assertEquals(u,u1);
            }
        }

        db.deleteUser(u.getId());
    }

    public void testUserNameExists() throws SQLException {
        User u = new User(-1,"quji","bla","bla@gmail.com",true);
        int iD = db.getUserByName("quji");
        if(iD!=-1){
            db.deleteUser(iD);
        }

        assertFalse(db.userNameExists("quji"));

        db.addUser(u);
        assertTrue(db.userNameExists("quji"));
        db.deleteUser(u.getId());
    }

    public void testAccountExists() throws SQLException {
        User u = new User(-1,"quji","bla","bla@gmail.com",true);
        int iD = db.getUserByName("quji");
        if(iD!=-1){
            db.deleteUser(iD);
        }

        assertFalse(db.accountExists(u.getUsername(),u.getPasswordHash()));

        db.addUser(u);
        assertTrue(db.accountExists(u.getUsername(),u.getPasswordHash()));
        db.deleteUser(u.getId());
    }

    public void testGetUser() throws SQLException {
        User u = new User(-1,"quji","bla","bla@gmail.com",true);
        int iD = db.getUserByName("quji");
        if(iD!=-1){
            db.deleteUser(iD);
        }

        assertNull(db.getUser(u.getUsername(),u.getPasswordHash()));
        db.addUser(u);
        assertEquals(u,db.getUser(u.getUsername(),u.getPasswordHash()));
        db.deleteUser(u.getId());
    }
}
