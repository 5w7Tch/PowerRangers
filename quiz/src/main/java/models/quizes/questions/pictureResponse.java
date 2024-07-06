package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class pictureResponse extends Question{

    private ArrayList<String> possibleAnswers;

    public pictureResponse(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(), jsonObject.get("answer").getAsJsonObject(), questionId,authorId,orderNum , "pictureResponse" ,jsonObject.get("score").getAsDouble());

        JsonArray array = answerJson.get("possibleAnswers").getAsJsonArray();
        possibleAnswers = new ArrayList<>();
        for (JsonElement obj : array){
            possibleAnswers.add(obj.getAsString());
        }

    }

    public pictureResponse(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
        JsonArray array = this.answerJson.get("possibleAnswers").getAsJsonArray();
        possibleAnswers = new ArrayList<>();
        for (JsonElement obj : array){
            possibleAnswers.add(obj.getAsString());
        }
    }

    @Override
    public String getQuestion(int orderNum) {
        String image = "<img src=\""+questionJson.get("pictureUrl").getAsString()+"\" alt=\"Image not Found\">";
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">describe!</div>\n" +
                image+
                "        <ul class=\"answers\">\n" +
                "            <div class=\"answer_response\" contenteditable=\"true\"name=\""+orderNum+"\" ></div>\n" +
                "        </ul>\n" +
                "    </div>";
        return html;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        return possibleAnswers.contains(answer[0]) ? score : 0;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        String image = "<img src=\""+questionJson.get("pictureUrl").getAsString()+"\" alt=\"Image not Found\">";
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">describe!</div>\n" +
                image+
                "        <ul class=\"answers\">\n" +
                "            <div class=\"answer_response\" contenteditable=\"true\"name=\"\" >"+ answer[0]+"</div>\n" +
                "        </ul>\n" +
                "    </div>";
        return html;
    }
}
