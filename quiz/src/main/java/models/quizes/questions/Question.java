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
    protected final double score;
    protected String type;

    public static void registerTypes(){
        questions.put("questionResponse", questionResponse.class);
        questions.put("fillInBlank", fillInBlank.class);
        questions.put("multipleAnswerQuestion" , multipleAnswerQuestion.class);
        questions.put("multipleAnswerChoice" , multipleAnswerChoice.class);
        questions.put("pictureResponse" , pictureResponse.class);
        questions.put("multipleChoice" , multipleChoice.class);
        questions.put("matching", matching.class);
    }

    public static Class<?> getClass(String type){
        return questions.get(type);
    }

    public Question(JsonObject questionJson, JsonObject answerJson, int questionId,int quizId,int orderNum , String type , double score){
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
        return type;
    }

    public double getScore(){
        return score;
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
        JsonObject obj = new JsonObject();
        obj.addProperty("type" , type);
        obj.add("question" , this.questionJson);
        obj.add("answer" , this.answerJson);
        obj.addProperty("score" , score);
        return obj;
    }

    public abstract String getQuestion(int orderNum);

    public abstract Double checkAnswer(String[] answer);
}
