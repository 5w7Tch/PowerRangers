package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class multipleAnswerChoice extends Question{
    private ArrayList<String> answers;
    public multipleAnswerChoice(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(), jsonObject.get("answer").getAsJsonObject(), questionId,authorId,orderNum , "multipleAnswerChoice" ,jsonObject.get("score").getAsDouble());

        JsonArray array = answerJson.get("answers").getAsJsonArray();
        answers = new ArrayList<>();
        for (JsonElement obj : array){
            answers.add(obj.getAsString());
        }

    }

    public multipleAnswerChoice(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
    }

    @Override
    public String getQuestion(int orderNum) {
        return null;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        int correctAns = 0;
        Boolean [] used = new Boolean[answers.size()];
        for (String curAns : answer){
            for (int i = 0; i < answers.size(); i++){
                if (!used[i] && curAns.equals(answers.get(i))){
                    correctAns++;
                    used[i] = true;
                }
            }
        }
        return (double)(correctAns / answers.size()) * score;
    }
}
