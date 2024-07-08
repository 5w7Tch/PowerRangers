package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class multipleAnswerChoice extends Question{
    private ArrayList<String> answers;
    public multipleAnswerChoice(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(), jsonObject.get("answer").getAsJsonObject(), questionId,authorId,orderNum , jsonObject.get("type").getAsString() ,jsonObject.get("score").getAsDouble());
        JsonArray array = this.answerJson.get("answers").getAsJsonArray();
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
    ArrayList<String> posAnswers;
    @Override
    public String getQuestion(int orderNum) {
        String html = "<div class=\"question-box\">\n" +
                "<h4 style=\"color: red\">Chose multiple answers</h4>"+
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" ;
        posAnswers = new ArrayList<>();
        Iterator<JsonElement> it1 =answerJson.get("answers").getAsJsonArray().iterator();
        Iterator<JsonElement> it = questionJson.get("wrongAnswers").getAsJsonArray().iterator();
        while (it.hasNext()){
            posAnswers.add(it.next().getAsString());
        }
        while (it1.hasNext()){
            posAnswers.add(it1.next().getAsString());
        }
        Collections.shuffle(posAnswers);
        for (int i = 0; i < posAnswers.size(); i++) {
            html += "<div class=\"answer_radio\" onclick=\"radioChangeMultiple(this, "+orderNum+")\" name=\""+orderNum+"\">"+posAnswers.get(i)+"</div>\n";
        }
        String end ="        </ul>\n" +
                "    </div>";
        return html+end;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        Double correctAns = 0.0;

        HashSet<String> hisAnswers = new HashSet<>(Arrays.asList(answer));

        for (int i = 0; i < answers.size(); i++){
            if (hisAnswers.contains(answers.get(i))){
                correctAns++;
                hisAnswers.remove(answers.get(i));
            }
        }
        correctAns -= hisAnswers.size();
        if(hisAnswers.contains("")){
            correctAns++;
        }
        if (correctAns < 0){
            correctAns = 0.0;
        }
        Double res = (correctAns/answers.size())*score;
        return res;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        HashSet<String> hisAnswers = new HashSet<>();

        for (int i = 0; i < answer.length; i++) {
            hisAnswers.add(answer[i]);
        }

        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" ;

        for (int i = 0; i < posAnswers.size(); i++) {
            if(hisAnswers.contains(posAnswers.get(i))){
                if(answers.contains(posAnswers.get(i))){
                    html += "<div class=\"answer_radio\"  name=\"0\" style=\"background-color: green;\">"+posAnswers.get(i)+"</div>\n";
                }else{
                    html += "<div class=\"answer_radio\"  name=\"0\" style=\"background-color: red;\">"+posAnswers.get(i)+"</div>\n";
                }
            }else{
                html += "<div class=\"answer_radio\" name=\"0\">"+posAnswers.get(i)+"</div>\n";
            }
        }
        String end ="        </ul>\n" +
                "    </div>";
        return html+end;
    }
}
