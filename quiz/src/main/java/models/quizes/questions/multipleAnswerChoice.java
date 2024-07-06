package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

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
        JsonArray array = this.answerJson.get("answers").getAsJsonArray();
        answers = new ArrayList<>();
        for (JsonElement obj : array){
            answers.add(obj.getAsString());
        }

    }

    @Override
    public String getQuestion(int orderNum) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" ;
        ArrayList<String> answers = new ArrayList<>();
        Iterator<JsonElement> it1 =answerJson.get("answers").getAsJsonArray().iterator();
        Iterator<JsonElement> it = questionJson.get("wrongAnswers").getAsJsonArray().iterator();
        while (it.hasNext()){
            answers.add(it.next().getAsString());
        }
        while (it1.hasNext()){
            answers.add(it1.next().getAsString());
        }
        Collections.shuffle(answers);
        for (int i = 0; i < answers.size(); i++) {
            html += "<div class=\"answer_radio\" onclick=\"radioChangeMultiple(this, "+orderNum+")\" name=\""+orderNum+"\">"+answers.get(i)+"</div>\n";
        }
        String end ="        </ul>\n" +
                "    </div>";
        return html+end;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        Double correctAns = 0.0;
        HashSet<String> hisAnswers = new HashSet<>(List.of(answer));
        for (int i = 0; i < answers.size(); i++){
            if (hisAnswers.contains(answers.get(i))){
                correctAns++;
                hisAnswers.remove(answers.get(i));
            }
        }
        correctAns -= hisAnswers.size();
        if (correctAns < 0){
            correctAns = 0.0;
        }
        return (correctAns/answers.size())*score;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        HashSet<String> hisAnswers = new HashSet<>(List.of(answer));

        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" ;
        ArrayList<String> answers = new ArrayList<>();
        Iterator<JsonElement> it1 =answerJson.get("answers").getAsJsonArray().iterator();
        Iterator<JsonElement> it = questionJson.get("wrongAnswers").getAsJsonArray().iterator();
        while (it.hasNext()){
            answers.add(it.next().getAsString());
        }
        while (it1.hasNext()){
            answers.add(it1.next().getAsString());
        }
        Collections.shuffle(answers);
        for (int i = 0; i < answers.size(); i++) {
            if(hisAnswers.contains(answers.get(i))){
                html += "<div class=\"answer_radio\" onclick=\"radioChangeMultiple(this, 0)\" name=\"0\" style=\"background-color: orange;\">"+answers.get(i)+"</div>\n";
            }else{
                html += "<div class=\"answer_radio\" onclick=\"radioChangeMultiple(this, 0)\" name=\"0\">"+answers.get(i)+"</div>\n";
            }
        }
        String end ="        </ul>\n" +
                "    </div>";
        return html+end;
    }
}
