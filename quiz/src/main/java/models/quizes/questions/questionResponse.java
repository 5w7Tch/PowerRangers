package models.quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class questionResponse extends Question{
    private String type;
    private String questionJson;
    private String answerJson;
    private double score;
    public questionResponse(JsonObject jsonObject,int questionId,int authorId,int orderNum){
        super(questionId,authorId,orderNum);
        this.type = "questionResponse";
        questionJson = jsonObject.get("question").getAsString();
        answerJson = jsonObject.get("answer").getAsString();
        score = jsonObject.get("score").getAsDouble();
    }

    public questionResponse(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super(questionId,quizId,orderNum);
        this.type = type;
        this.questionJson = questionJson;
        this.answerJson = answerJson;
        this.score = score;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public JsonObject generateJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type",this.type);
        object.addProperty("question", this.questionJson);
        object.addProperty("answer", this.answerJson);
        object.addProperty("score",this.score);
        return object;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getQuestionJson() {
        return this.questionJson;
    }

    @Override
    public String getAnswerJson() {
        return this.answerJson;
    }

    public Double checkAnswer(String[] answer){
        if (answer[0].equals(getAnswerJson())) {
            return getScore();
        }
        return 0.0;
    }

    @Override
    public String getQuestion(int orderNumber) {
        String question = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">"+getQuestionJson()+"</div>\n" +
                "        <ul class=\"answers\">\n" +
                "            <div class=\"answer_response\" contenteditable=\"true\"name=\""+orderNumber+"\"></div>\n" +
                "        </ul>\n" +
                "    </div>";

        return question;
    }
}
