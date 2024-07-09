package Dao;

import junit.framework.TestCase;
import models.DAO.Dao;
import models.DAO.dbCredentials;
import models.DAO.mySqlDb;
import models.USER.User;
import models.quizes.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

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

    public void testQuizExists() throws SQLException {
        assertFalse(db.quizExists(1));
        Quiz quiz = new Quiz(1,1,"b",new Date(System.currentTimeMillis()),"",true,true,4,true);
        User u = new User(1,"","","",true);
        db.addUser(u);
        db.addQuiz(quiz);
        assertTrue(db.quizExists(1));
    }

    public void testGetQuiz() throws SQLException {
        testQuizExists();
        Quiz q = db.getQuiz("1");
        assertEquals(1,q.getId());
        assertEquals(1,q.getAuthor());
        assertEquals("b",q.getName());
    }

    public void testAddQuiz() throws SQLException {
        User u = new User(1,"","","",true);
        db.addUser(u);
        Quiz quiz = new Quiz(1,1,"b",new Date(System.currentTimeMillis()),"",true,true,4,true);
        db.addQuiz(quiz);
        Quiz q = db.getQuiz("1");
        assertEquals(quiz.getId(),q.getId());
        assertEquals(quiz.getName(),q.getName());
    }

    public void testQuizCount() throws SQLException {
        User u = new User(1,"","","",true);
        db.addUser(u);
        assertEquals(0,db.getQuizCount().intValue());
        Quiz quiz = new Quiz(1,1,"b",new Date(System.currentTimeMillis()),"",true,true,4,true);
        db.addQuiz(quiz);
        assertEquals(1,db.getQuizCount().intValue());
        db.addQuiz(quiz);
        assertEquals(2,db.getQuizCount().intValue());
    }

    public void testUpdateQuiz() throws SQLException {
        User u = new User(1,"","","",true);
        db.addUser(u);
        Quiz quiz = new Quiz(1,1,"b",new Date(System.currentTimeMillis()),"",true,true,4,true);
        db.addQuiz(quiz);
        assertEquals("b",quiz.getName());
        Quiz quiz1 = new Quiz(1,1,"c",new Date(System.currentTimeMillis()),"",true,true,4,true);
        db.updateQuiz(quiz1);
        assertEquals("c",db.getQuiz("1").getName());
    }




}
