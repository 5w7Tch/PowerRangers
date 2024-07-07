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
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">Fill in!</div>\n" +
                "        <div class=\"question-text\">\n";

        String field = "<div class=\"answer_response\" contenteditable=\"true\" name=\""+orderNum+"\" style=\"width: 60px\"></div>\n";
        String quest = questionJson.get("question").getAsString();
        for (int i = 0; i < quest.length(); i++) {
            if(quest.charAt(i) == '★'){
                html+= field;
            }else{
                html+= quest.charAt(i);
            }
        }

        String end ="        </div>\n" +
                "    </div>";
        return html+end;
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

    @Override
    public String getAnsweredQuestion(String[] answer) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">Fill in!</div>\n" +
                "        <div class=\"question-text\">\n";

        String quest = questionJson.get("question").getAsString();
        int idx = 0;
        for (int i = 0; i < quest.length(); i++) {
            if(quest.charAt(i) == '★'){
                html+= "<div class=\"answer_response\" name=\"0\" style=\"width: 60px\">"+answer[idx]+"</div>\n";
                idx++;
            }else{
                html+= quest.charAt(i);
            }
        }
        String end ="        </div>\n" +
                "    </div>";
        return html+end;
    }
}
