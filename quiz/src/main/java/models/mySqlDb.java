package models;

import org.apache.commons.dbcp2.BasicDataSource;

public class mySqlDb implements Dao{
    private final BasicDataSource dbSource;

    public mySqlDb(BasicDataSource source){
        dbSource = source;
    }


    @Override
    public void closeDbConnection() {

    }
}
