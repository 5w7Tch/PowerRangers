package models.quizes.questions;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public abstract class Question {
    private static final Map<String, Class<?>> questions = new HashMap<>();
    protected int quizId;
    protected int orderNum;
    protected int questionId;
    protected JsonObject questionJson;
    protected JsonObject answerJson;
    protected String type;
    protected double score;

    public static void registerTypes(){
        questions.put("questionResponse", questionResponse.class);
        questions.put("multipleChoice",multipleChoice.class);
    }

    public static Class<?> getClass(String type){
        return questions.get(type);
    }

    public Question(JsonObject questionJson,JsonObject answerJson,int questionId,int quizId,int orderNum,String type,double score){
        this.questionId = questionId;
        this.quizId = quizId;
        this.orderNum = orderNum;
        this.questionJson = questionJson;
        this.answerJson = answerJson;
        this.type = type;
        this.score = score;
    }
    
    public void printObj(){
        System.out.println(getQuestionId());
        System.out.println(getType());
        System.out.println(getQuestionJson());
        System.out.println(getAnswerJson());
        System.out.println(getOrderNum());
        System.out.println(getScore());
        System.out.println(getQuizId());
    }

    public String getQuestionJson(){
        return questionJson.toString();
    }

    public String getAnswerJson(){
        return answerJson.toString();
    }

    public String getType(){
        return this.type;
    }

    public double getScore(){
        return this.score;
    }

    public int getQuizId(){
        return quizId;
    }

    public int getOrderNum(){
        return orderNum;
    }

    public int getQuestionId(){
        return questionId;
    }

    public void setQuestionId(int id){
        questionId=id;
    }

    public JsonObject generateJson(){
        JsonObject object = new JsonObject();
        object.addProperty("type",this.type);
        object.add("question",this.questionJson);
        object.add("answer",this.answerJson);
        object.addProperty("score",this.score);
        return object;
    }

    public abstract String getQuestion(int orderNum);

    public abstract Double checkAnswer(String[] answer);
}
