package models;

import org.apache.commons.dbcp2.BasicDataSource;

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
}
