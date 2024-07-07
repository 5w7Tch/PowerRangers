package models.USER;



import com.mysql.cj.conf.ConnectionUrlParser;
import models.activity.Activity;
import models.enums.ActivityType;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class WritenQuiz extends Activity
{
    Double score;
    String scoreString;
    Double time;
    String timeString;
    String dateString;
    int quizId;
    String writerName;
    static DecimalFormat df = new DecimalFormat("#.00");

    public WritenQuiz(int id, Double score, Date date, Double time, int quizId, int userId, String writerName) {
        super(id, userId, date, ActivityType.WROTE_QUIZ);
        this.score = score;
        this.scoreString = score.toString();
        this.time = time;
        this.timeString = df.format(time);
        this.dateString = date.toString();
        this.quizId = quizId;
        this.writerName = writerName;
    }

    public Double getScore() {
        return score;
    }

    public Double getTime() {
        return time;
    }

    public Date getDate() {
        return super.getSendTime();
    }

    public int getQuizId() {
        return quizId;
    }

    public String getWriterName() {return writerName;}

    public int getUserId() {
        return super.getFromId();
    }

    public static String getAvgScore(ArrayList<WritenQuiz> writtenQuizList){
        Double res = 0.0;
        for(WritenQuiz w: writtenQuizList){
            res += w.getScore();
        }
        return df.format(res/writtenQuizList.size());
    }
    public static Date getLastWritenDate(ArrayList<WritenQuiz> writtenQuizList){
        Date res = new Date(0);
        for(WritenQuiz w: writtenQuizList){
            if(res.getTime() < w.getDate().getTime()){res = w.getDate();}
        }
        return res;
    }

    public static String getAvgTime(ArrayList<WritenQuiz> writtenQuizList){
        Double res = 0.0;
        for(WritenQuiz w: writtenQuizList){res += w.getTime();}
        return df.format(res/writtenQuizList.size());
    }

    public static ConnectionUrlParser.Pair<Integer, String> getTopScorer(ArrayList<WritenQuiz> writtenQuizList){
        ConnectionUrlParser.Pair<Integer, String> res = new ConnectionUrlParser.Pair<>(1, "1");
        Double Score = 0.0;
        for(WritenQuiz w: writtenQuizList){
            if(Double.compare(Score, w.getScore())<0){
                res = new ConnectionUrlParser.Pair<>( w.getUserId(), w.getWriterName());
                Score = w.getScore();
            }
        }
        return res;
    }

    public String getTimeString() {
        return timeString;
    }

    public String getScoreString() {
        return scoreString;
    }

    public String getDateString() {
        return dateString;
    }
}
