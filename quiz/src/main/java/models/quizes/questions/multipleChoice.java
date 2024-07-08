package models.quizes.questions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class  multipleChoice extends Question{

    public multipleChoice(JsonObject jsonObject,int questionId,int authorId,int orderNum){
        super(jsonObject.get("question").getAsJsonObject(),jsonObject.get("answer").getAsJsonObject(),questionId,authorId,orderNum,jsonObject.get("type").getAsString(),jsonObject.get("score").getAsDouble()); ;
    }

    public multipleChoice(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson),(JsonObject) JsonParser.parseString(answerJson),questionId,quizId,orderNum,type,score);
    }

    @Override
    public String getQuestion(int orderNum) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n";
        ArrayList<String> answers = new ArrayList<>();
        String answer =answerJson.get("answer").getAsString();
        answers.add(answer);
        Iterator<JsonElement> it = questionJson.get("wrongAnswers").getAsJsonArray().iterator();
        while (it.hasNext()){
            answers.add(it.next().getAsString());
        }
        Collections.shuffle(answers);
        for (int i = 0; i < answers.size(); i++) {
            html += "<div class=\"answer_radio\" onclick=\"radioChange(this, "+orderNum+")\" name=\""+orderNum+"\">"+answers.get(i)+"</div>\n";
        }
        String end = "        </ul>\n" +
                "    </div>";

        return html+end;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        return answer[0].equals(questionJson.get("correctAnswer").getAsString()) ? this.score : 0.0;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n";
        ArrayList<String> answers = new ArrayList<>();
        String correctAnswer =answerJson.get("answer").getAsString();
        answers.add(correctAnswer);
        Iterator<JsonElement> it = questionJson.get("wrongAnswers").getAsJsonArray().iterator();
        while (it.hasNext()){
            answers.add(it.next().getAsString());
        }
        Collections.shuffle(answers);
        for (int i = 0; i < answers.size(); i++) {
            if(answers.get(i).equals(answer[0])){
                if(checkAnswer(answer).equals(0.0)){
                    html += "<div class=\"answer_radio\" name=\"0\" style=\"background-color: red;\">"+answers.get(i)+"</div>\n";
                }else{
                    html += "<div class=\"answer_radio\" name=\"0\" style=\"background-color: green;\">"+answers.get(i)+"</div>\n";
                }
            }else{
                html += "<div class=\"answer_radio\" name=\"0\">"+answers.get(i)+"</div>\n";
            }
        }
        String end = "        </ul>\n" +
                "    </div>";

        return html+end;
    }
}
