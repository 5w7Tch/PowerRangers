package models.USER;



import com.mysql.cj.conf.ConnectionUrlParser;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;

public class WritenQuiz {
    Double score;
    Double time;
    Date date;
    int userId;
    int quizId;
    String writerName;

    public WritenQuiz(Double score, Date date, Double time, int quizId, int userId, String writerName) {
        this.score = score;
        this.date = date;
        this.time = time;
        this.quizId = quizId;
        this.userId = userId;
        this.writerName = writerName;
    }

    public Double getScore() {
        return score;
    }

    public Double getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public int getQuizId() {
        return quizId;
    }
    public String getWriterName() {return writerName;}

    public int getUserId() {
        return userId;
    }
    public static Double getAvgScore(ArrayList<WritenQuiz> writtenQuizList){
        Double res = new Double(0);
        for(WritenQuiz w: writtenQuizList){
            res += w.getScore();
        }
        return res/writtenQuizList.size();
    }
    public static Date getLastWritenDate(ArrayList<WritenQuiz> writtenQuizList){
        Date res = new Date(0);
        for(WritenQuiz w: writtenQuizList){
            if(res.getTime() < w.getDate().getTime()){
                res = w.getDate();
            }
        }
        return res;
    }

    public static Double getAvgTime(ArrayList<WritenQuiz> writtenQuizList){
        Double res = new Double(0);
        for(WritenQuiz w: writtenQuizList){
            res += w.getTime();
        }
        return res/writtenQuizList.size();
    }

    public static ConnectionUrlParser.Pair<Integer, String> getTopScorer(ArrayList<WritenQuiz> writtenQuizList){
        ConnectionUrlParser.Pair<Integer, String> res = new ConnectionUrlParser.Pair<>(1, "1");
        Double Score = new Double(0);
        for(WritenQuiz w: writtenQuizList){
            if(Double.compare(Score, w.getScore())<0){
                res = new ConnectionUrlParser.Pair<>( w.getUserId(), w.getWriterName());
                Score = w.getScore();
            }
        }
        return res;
    }
}
