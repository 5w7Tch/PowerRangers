package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;

import java.util.*;

public class multipleAnswerQuestion extends Question{

    private ArrayList<String> answers;
    private boolean orderMatters;

    public multipleAnswerQuestion(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject() , jsonObject.get("answer").getAsJsonObject(),  questionId,authorId,orderNum , jsonObject.get("type").getAsString() ,  jsonObject.get("score").getAsDouble());
        initAnswersList();
    }

    public multipleAnswerQuestion(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject)JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
        initAnswersList();
    }

    private void initAnswersList(){
        this.answers = new ArrayList<>();

        JsonArray array = this.answerJson.get("answers").getAsJsonArray();
        answers = new ArrayList<>();
        for (JsonElement obj : array){
            answers.add(obj.getAsString());
        }

        this.orderMatters = this.questionJson.get("orderMatters").getAsBoolean();
    }


    @Override
    public String getQuestion(int orderNum) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n";
        Iterator<JsonElement> it = answerJson.get("answers").getAsJsonArray().iterator();

        while (it.hasNext()){
            it.next();
            html += "<div class=\"answer_response\" contenteditable=\"true\"name=\""+orderNum+"\"></div>\n";
        }
        String end ="        </ul>\n" +
                    "    </div>";


        return html+end;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        double correctAns = 0.0;
        if (this.orderMatters){
            for (int i = 0; i < this.answers.size(); i++){
                if (this.answers.get(i).equals(answer[i]))
                    correctAns++;
            }
        }else{
            HashSet<String> hisAnswers = new HashSet<>(List.of(answer));
            for (int i = 0; i < this.answers.size(); i++){
                if (hisAnswers.contains(this.answers.get(i))){
                    correctAns++;
                    hisAnswers.remove(this.answers.get(i));
                }
            }
        }
        return (correctAns/this.answers.size()) * this.score;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n";
        Iterator<JsonElement> it = answerJson.get("answers").getAsJsonArray().iterator();
        int i = 0;
        while (it.hasNext()){
            it.next();
            html += "<div class=\"answer_response\" name=\"0\">"+answer[i]+"</div>\n";
            i++;
        }
        String end ="        </ul>\n" +
                "    </div>";


        return html+end;
    }
}
