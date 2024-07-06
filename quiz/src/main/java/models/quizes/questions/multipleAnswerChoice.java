package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class multipleAnswerChoice extends Question{
    private ArrayList<String> answers;
    public multipleAnswerChoice(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(), jsonObject.get("answer").getAsJsonObject(), questionId,authorId,orderNum , "multipleAnswerChoice" ,jsonObject.get("score").getAsDouble());
        initAnswersList();
    }

    public multipleAnswerChoice(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
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
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" ;
        ArrayList<String> answers = new ArrayList<>();
        Iterator<JsonElement> it1 =questionJson.get("correctAnswers").getAsJsonArray().iterator();
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
        int correctAns = 0;
        ArrayList<Boolean> used = new ArrayList<>(Collections.nCopies(answers.size(), false));

        for (String curAns : answer){
            for (int i = 0; i < answers.size(); i++){
                if (!used.get(i) && curAns.equals(answers.get(i))){
                    correctAns++;
                    used.set(i, true);
                }
            }
        }
        return (double)(correctAns / answers.size()) * score;
    }
}
