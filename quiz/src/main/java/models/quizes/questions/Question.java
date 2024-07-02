package models.quizes.questions;

import java.util.HashMap;
import java.util.Map;

public abstract class Question {
    private static final Map<String, Class<?>> questions = new HashMap<>();
    protected int quizId;
    protected int orderNum;

    public static void registerTypes(){
        questions.put("questionResponse", questionResponse.class);
    }

    public static Class<?> getClass(String type){
        return questions.get(type);
    }

    public Question(int quizId,int orderNum){
        this.quizId = quizId;
        this.orderNum = orderNum;
    }
    
    public abstract void printObj();

    public abstract String getQuestionJson();



    public abstract String getType();

    public abstract int getScore();

    public int getQuizId(){
        return quizId;
    }

    public int getOrderNum(){
        return orderNum;
    }
}
