package quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.multipleAnswerChoice;

public class multipleAnswerChoiceTest extends TestCase {
    public void test1(){
        JsonObject obj = new JsonObject();
        obj.addProperty("score",23);
        obj.addProperty("type","multipleAnswerChoiceTest");
        obj.add("question",new JsonObject());
        String jsonObj = "{answers:['a','b','c']}";
        JsonObject ansObj = (JsonObject) JsonParser.parseString(jsonObj);
        obj.add("answer",ansObj);

        Question q = new multipleAnswerChoice(obj,1,1,2);
        String[] answers = new String[]{"a","b"};
        double expected = 23*2.0/3;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));
    }

    public void test2(){
        String jsonObj = "{answers:['a','b','c']}";
        Question q = new multipleAnswerChoice(1,1,"multipleAnswerChoiceTest","{}",jsonObj,0,14);
        String[] answers = new String[]{"a","b"};
        double expected = 14*2.0/3;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));
    }
}
