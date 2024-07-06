package models.quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class matching extends Question{
    public matching(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(), jsonObject.get("answer").getAsJsonObject(), questionId,authorId,orderNum , jsonObject.get("type").getAsString() ,jsonObject.get("score").getAsDouble());
        initAnswersList();
    }

    public matching(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
        initAnswersList();
    }

    private void initAnswersList(){

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
