package models.quizes.questions;

import com.google.gson.JsonObject;

public class fillInBlank extends Question{
    private final String type;

    public fillInBlank(JsonObject jsonObject,int questionId,int authorId,int orderNum){
        super(questionId,authorId,orderNum);
        this.type = "fillInBlank";
    }

    public fillInBlank(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super(questionId,quizId,orderNum);
        this.type = type;
    }

    @Override
    public String getQuestionJson() {
        return "";
    }

    @Override
    public String getAnswerJson() {
        return "";
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public JsonObject generateJson() {
        return null;
    }

    @Override
    public String getQuestion(int orderNum) {
        return "";
    }

    @Override
    public Double checkAnswer(String[] answer) {
        return 0.0;
    }
}
