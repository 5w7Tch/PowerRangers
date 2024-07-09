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
                "        <div class=\"question-text\" style=\"\">\n";

        String field = "<div class=\"answer_response\" contenteditable=\"true\" name=\""+orderNum+"\" style=\"padding: 1px; margin-bottom: 0px; width: 100px;\"></div>";
        String quest = questionJson.get("question").getAsString();
        for (int i = 0; i < quest.length(); i++) {
            if(quest.startsWith("<>", i)){
                html+= field;
                i++;
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
            if(answer[i].trim().equalsIgnoreCase(answers.get(i).trim())){
                correctAnswers++;
            }
        }

        double score = this.score * ((double)correctAnswers/answer.length);

        return Math.round(score * 100.0)/100.0;
    }

    private boolean isCorrect(String ans, int idx){
        System.out.println(ans);
        System.out.println(answers.get(idx));
        return ans.trim().equalsIgnoreCase(answers.get(idx).trim());
    }
    @Override
    public String getAnsweredQuestion(String[] answer) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">Fill in!</div>\n" +
                "        <div class=\"question-text\" style=\"\">\n";

        String quest = questionJson.get("question").getAsString();
        int idx = 0;
        for (int i = 0; i < quest.length(); i++) {
            if(quest.startsWith("<>", i)){
                if(isCorrect(answer[idx], idx)){
                    html+= "<div class=\"answer_response\" name=\"0\" style=\"background-color: green;padding: 1px; margin-bottom: 0px;width: 100px;\" >"+answer[idx]+"</div>";
                }else{
                    html+= "<div class=\"answer_response\" name=\"0\" style=\"background-color: red; width: 100px; padding: 1px; margin-bottom: 0px;\" >"+answer[idx]+"</div>";
                }
                idx++;
                i++;
            }else{
                html+= quest.charAt(i);
            }
        }
        String end ="        </div>\n" +
                "    </div>";
        return html+end;
    }
}
