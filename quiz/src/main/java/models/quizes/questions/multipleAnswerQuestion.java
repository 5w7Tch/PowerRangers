package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class multipleAnswerQuestion extends Question{

    private ArrayList<String> answers;
    private boolean orderMatters;

    public multipleAnswerQuestion(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject() , jsonObject.get("answer").getAsJsonObject(),  questionId,authorId,orderNum , "multipleAnswerQuestion" ,  jsonObject.get("score").getAsDouble());
        this.answers = new ArrayList<>();

        JsonArray array = answerJson.get("answers").getAsJsonArray();
        answers = new ArrayList<>();
        for (JsonElement obj : array){
            answers.add(obj.getAsString());
        }

        this.orderMatters = questionJson.get("orderMatters").getAsBoolean();
    }

    public multipleAnswerQuestion(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject)JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
    }

    @Override
    public String getQuestion(int orderNum) {
        return null;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        int correctAns = 0;
        if (this.orderMatters){
            for (int i = 0; i < this.answers.size(); i++){
                if (this.answers.get(i).equals(answer[i]))
                    correctAns++;
            }
        }else{
            Boolean [] used = new Boolean[this.answers.size()];
            for (int i = 0; i < this.answers.size(); i++){
                for (int j = 0; j < this.answers.size(); j++){
                    if (!used[j] && this.answers.get(i).equals(answer[j])){
                        correctAns++;
                        used[j] = true;
                    }
                }
            }
        }
        return (double)(correctAns / this.answers.size()) * this.score;
    }
}
