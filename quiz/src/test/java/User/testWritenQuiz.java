package User;

import com.mysql.cj.conf.ConnectionUrlParser;
import junit.framework.TestCase;
import models.USER.WritenQuiz;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class testWritenQuiz extends TestCase {
    ArrayList<WritenQuiz> writtenQuizzes;
    static DecimalFormat df = new DecimalFormat("#.00");
    Date date;
    @Override
    protected void setUp() throws Exception {
        writtenQuizzes = new ArrayList<>();
        date = new Date();
        for (double i = 1.0; i <= 10.0; i++) {
            writtenQuizzes.add(new WritenQuiz(i, date, i, 1,2,"jaja"));
        }
    }

    public void testConstructor(){
        Date date = new Date();
        WritenQuiz wq = new WritenQuiz(20.0, date, 25.0, 1, 2, "jaja");
        assertEquals(20.0, wq.getScore());
        assertEquals(date, wq.getDate());
        assertEquals(25.0, wq.getTime());
        assertEquals(1,wq.getQuizId());
        assertEquals(2, wq.getUserId());
        assertEquals("jaja", wq.getWriterName());
        assertEquals("25.0", wq.getTimeString());
        assertEquals("20.0", wq.getScoreString());
        assertEquals(date.toString(), wq.getDateString());

    }

    public void testAvgScore(){
        double num = 0.0;
        for (double i = 1.0; i <= 10.0; i++) {
           num += i;
        }
        num /= 10.0;
        assertEquals(df.format(num), WritenQuiz.getAvgScore(writtenQuizzes));
    }

    public void testAvgTime(){
        double num = 0.0;
        for (double i = 1.0; i <= 10.0; i++) {
            num += i;
        }
        num /= 10.0;
        assertEquals(df.format(num), WritenQuiz.getAvgTime(writtenQuizzes));
    }

    public void testTopScorer(){
        ConnectionUrlParser.Pair<Integer, String> res = WritenQuiz.getTopScorer(writtenQuizzes);
        int num = res.left;
        assertEquals(2, num);
        assertEquals("jaja", res.right);
    }

    public void testLastWrittenDate(){
        assertEquals(date, WritenQuiz.getLastWritenDate(writtenQuizzes));
        Date newDate = new Date();
        WritenQuiz wq = new WritenQuiz(20.0, newDate, 25.0, 1, 2, "jaja");
        writtenQuizzes.add(wq);
        assertEquals(newDate, WritenQuiz.getLastWritenDate(writtenQuizzes));
    }

}
