package models.quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class questionResponse extends Question{
    public questionResponse(JsonObject jsonObject,int questionId,int authorId,int orderNum){
        super(jsonObject.get("question").getAsJsonObject(),jsonObject.get("answer").getAsJsonObject(),questionId,authorId,orderNum,jsonObject.get("type").getAsString(),jsonObject.get("score").getAsDouble()); ;
    }

    public questionResponse(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson),(JsonObject) JsonParser.parseString(answerJson),questionId,quizId,orderNum,type,score);
    }

    @Override
    public JsonObject generateJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type",this.type);
        object.add("question", this.questionJson);
        object.add("answer", this.answerJson);
        object.addProperty("score",this.score);
        return object;
    }
    public Double checkAnswer(String[] answer){
        if (answer[0].equals(answerJson.get("description").getAsString())) {
            return getScore();
        }
        return 0.0;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        String question = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" +
                "            <div class=\"answer_response\"name=\"0\">"+answer[0]+"</div>\n" +
                "        </ul>\n" +
                "    </div>";

        return question;
    }

    @Override
    public String getQuestion(int orderNumber) {
        String question = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+questionJson.get("description").getAsString()+"</div>\n" +
                "        <ul class=\"answers\">\n" +
                "            <div class=\"answer_response\" contenteditable=\"true\"name=\""+orderNumber+"\"></div>\n" +
                "        </ul>\n" +
                "    </div>";

        return question;
    }
}
