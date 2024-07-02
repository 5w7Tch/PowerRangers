package models.quizes.questions;

import com.google.gson.JsonObject;

public class questionResponse extends Question{
    private String type;
    private String question;
    private String answer;
    private int score;
    public questionResponse(JsonObject jsonObject,int authorId,int orderNum){
        super(authorId,orderNum);
        this.type = "questionResponse";
        question = jsonObject.get("question").getAsString();
        answer = jsonObject.get("answer").getAsString();
        score = jsonObject.get("score").getAsInt();
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public int getScore() {
        return score;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String getType() {
        return type;
    }


    @Override
    public void printObj() {
        System.out.println(type+" "+question+" "+answer+" "+score);
    }

    @Override
    public String getQuestionJson() {
        return "";
    }
}
