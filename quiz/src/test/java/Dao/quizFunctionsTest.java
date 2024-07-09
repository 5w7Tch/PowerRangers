package Dao;

import junit.framework.TestCase;
import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class quizFunctionsTest extends TestCase {
    private Dao db;
    private BasicDataSource source;

    public quizFunctionsTest(){
        source = new BasicDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/quiztestdb");
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);
        db = new mySqlDb(source);
    }

    private void executeSqlFile(String filePath, Connection connection) throws SQLException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            String[] sqlCommands = sql.toString().split(";");
            Statement statement = connection.createStatement();
            for (String command : sqlCommands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command.trim());
                }
            }
        }
    }

    public void setUp() throws SQLException {
        try(Connection con = source.getConnection()){
            executeSqlFile("src/main/java/sql/testDatabase.sql",con);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testQuizExists(){

    }



}
