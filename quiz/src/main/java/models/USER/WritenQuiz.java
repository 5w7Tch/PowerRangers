package models.USER;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;

public class WritenQuiz {
    Double score;
    Double time;
    Date date;
    int userId;
    int quizId;


    public WritenQuiz(Double score, Date date, Double time, int quizId, int userId) {
        this.score = score;
        this.date = date;
        this.time = time;
        this.quizId = quizId;
        this.userId = userId;
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

    public static Integer getTopScorer(ArrayList<WritenQuiz> writtenQuizList){
        Integer res = 0;
        Double Score = new Double(0);
        for(WritenQuiz w: writtenQuizList){
            if(Double.compare(Score, w.getScore())<0){
                res = w.getUserId();
                Score = w.getScore();
            }
        }
        return res;
    }
}
