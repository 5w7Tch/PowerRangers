package models.quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;


public class matching extends Question{
    private ArrayList<String> left,right;
    private HashMap<String, String> anss;
    public matching(JsonObject jsonObject, int questionId, int authorId, int orderNum){
        super(jsonObject.get("question").getAsJsonObject(), jsonObject.get("answer").getAsJsonObject(), questionId,authorId,orderNum , jsonObject.get("type").getAsString() ,jsonObject.get("score").getAsDouble());
        initAnswersList();
    }

    public matching(int questionId,int quizId,String type,String questionJson,String answerJson,int orderNum,double score){
        super((JsonObject) JsonParser.parseString(questionJson) , (JsonObject)JsonParser.parseString(answerJson) ,questionId,quizId,orderNum , type , score);
        initAnswersList();
    }

    private void initAnswersList(){
        left = new ArrayList<>();
        right = new ArrayList<>();
        anss = new HashMap<>();
        JsonArray leftArr = answerJson.get("left").getAsJsonArray();
        JsonArray rightArr = answerJson.get("right").getAsJsonArray();

        for(int i=0;i<leftArr.size();i++){
            left.add(leftArr.get(i).getAsString());
            right.add(rightArr.get(i).getAsString());
            anss.put(leftArr.get(i).getAsString(),rightArr.get(i).getAsString());
        }
    }

    @Override
    public String getQuestion(int orderNum) {
        Collections.shuffle(left);
        Collections.shuffle(right);

        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">Match.</div>\n" +
                "        <div class=\"match\">\n"+
                "            <ul class=\"answers\">\n";

        for (int i = 0; i < left.size(); i++) {
            html += "<h5>"+(i+1)+". <div class=\"match_option\">"+left.get(i)+"</div></h5>\n";
        }
        html += " </ul>\n"+
                " <ul class=\"answers\">\n";
        for (int i = 0; i < right.size(); i++) {
            html += "<h5>"+((char)('a'+i))+". <div class=\"match_option\">"+right.get(i)+"</div></h5>\n";
        }

        String middle =" </ul>\n"+
                "       </div>\n" +
                "<h4 style=\"color: red\">Write corresponding sentences character</h4>"+
                "        <div class=\"dropdown-container\">\n";

        html += middle;
        for (int i = 0; i < right.size(); i++) {
            html += "      <h5>"+(i+1)+".\n" +
                    "                <div class=\"answer_response\" contenteditable=\"true\"name=\""+orderNum+"\" ></div>\n" +
                    "            </h5>\n";
        }

        String end = "        </div>\n" +
                "    </div>";
        return html+end;
    }

    @Override
    public Double checkAnswer(String[] answer) {
        Double correct = 0.0;
        for (int i = 0; i < answer.length; i++) {
            String l = left.get(i);
            String hisAns = answer[i].trim().toLowerCase();
            if(hisAns.length()==1){
                int placeInR = hisAns.charAt(0)-'a';
                String r = right.get(placeInR);
                if(r.equals(anss.get(l))){
                    correct++;
                }
            }
        }
        return (correct/right.size())*score;
    }

    @Override
    public String getAnsweredQuestion(String[] answer) {
        String html = "<div class=\"question-box\">\n" +
                "        <div class=\"question-text\">Match.</div>\n" +
                "        <div class=\"match\">\n"+
                "            <ul class=\"answers\">\n";

        for (int i = 0; i < left.size(); i++) {
            html += "<h5>"+(i+1)+". <div class=\"match_option\">"+left.get(i)+"</div></h5>\n";
        }
        html += " </ul>\n"+
                " <ul class=\"answers\">\n";
        for (int i = 0; i < right.size(); i++) {
            html += "<h5>"+((char)('a'+i))+". <div class=\"match_option\">"+right.get(i)+"</div></h5>\n";
        }

        String middle =" </ul>\n"+
                "       </div>\n" +
                "        <div class=\"dropdown-container\">\n";
        html += middle;
        for (int i = 0; i < right.size(); i++) {
            if(isCorrect(answer[i], i)){
                html += "      <h5>"+(i+1)+".\n" + "<div class=\"answer_response\" name=\"3\" style=\"background-color: green;\">"+answer[i]+"</div>\n" + "            </h5>\n";
            }else{
                html += "      <h5>"+(i+1)+".\n" + "<div class=\"answer_response\" name=\"3\" style=\"background-color: red;\">"+answer[i]+"</div>\n" + "            </h5>\n";
            }
        }

        String end = "        </div>\n" +
                "    </div>";
        return html+end;
    }

    private boolean isCorrect(String s, int i) {
        String l = left.get(i);
        String hisAns = s.trim().toLowerCase();
        if(hisAns.length()==1){
            int placeInR = hisAns.charAt(0)-'a';
            String r = right.get(placeInR);
            if(r.equals(anss.get(l))){
                return true;
            }
        }
        return false;
    }
}
