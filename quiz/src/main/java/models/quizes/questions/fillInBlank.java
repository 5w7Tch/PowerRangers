package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class fillInBlank extends Question{
    private ArrayList<String> answers;
    public fillInBlank(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(),jsonObject.get("answer").getAsJsonObject(),questionId,authorId,orderNum,jsonObject.get("type").getAsString(),jsonObject.get("score").getAsDouble()); ;
        initAnswersList();
    }

    public fillInBlank(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson),(JsonObject) JsonParser.parseString(answerJson),questionId,quizId,orderNum,type,score);
        initAnswersList();
    }

    private void initAnswersList(){
        JsonArray array = this.answerJson.get("answers").getAsJsonArray();
        answers = new ArrayList<>();
        for (JsonElement obj : array){
            answers.add(obj.getAsString());
        }
    }

    @Override
    public String getQuestion(int orderNum) {
        return "";
    }

    @Override
    public Double checkAnswer(String[] answer) {
        int correctAnswers = 0;
        for(int i=0;i<answer.length;i++){
            if(answer[i].equals(answers.get(i))){
                correctAnswers++;
            }
        }

        double score = this.score * ((double)correctAnswers/answer.length);

        return Math.round(score * 100.0)/100.0;
    }
}
