package quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.multipleChoice;
import models.quizes.questions.pictureResponse;

public class pictureResponseTest extends TestCase {
    public void test1(){
        JsonObject obj = new JsonObject();
        obj.addProperty("score",23);
        obj.addProperty("type","pictureResponse");
        obj.add("question", JsonParser.parseString("{}"));
        String jsonObj = "{possibleAnswers:['a','b','c']}";
        JsonObject ansObj = (JsonObject) JsonParser.parseString(jsonObj);
        obj.add("answer",ansObj);

        Question q = new pictureResponse(obj,1,1,2);
        String[] answers = new String[]{"a"};
        double expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));
    }

    public void test2(){
        Question q = new pictureResponse(1,1,"pictureResponse","{}","{possibleAnswers:['a','b','c']}",2,23);
        String[] answer = new String[]{"a"};
        double expected = 23;
        assertEquals(Math.round(23*100.0)/100.0,q.checkAnswer(answer));
    }
}
