package quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.fillInBlank;
import models.quizes.questions.matching;

public class matchingTest extends TestCase {
    public void test1(){
        JsonObject obj = new JsonObject();
        obj.addProperty("score",23);
        obj.addProperty("type","matching");
        obj.add("question",new JsonObject());
        String jsonObj = "{left:['a','1'],right:['b','2']}";
        JsonObject ansObj = (JsonObject) JsonParser.parseString(jsonObj);
        obj.add("answer",ansObj);

        Question quest = new matching(obj,1,0,0);
        String[] anss = new String[]{"a","b"};
        assertEquals(quest.getScore(),quest.checkAnswer(anss));
    }

    public void test2(){
        String jsonObj = "{left:['a','1','3'],right:['b','2','4']}";

        Question quest = new matching(1,2,"matching","{}",jsonObj,0,23);
        String[] anss = new String[]{"a","c","b"};
        double expectedScore = 23.0/3;
        assertEquals(Math.round(expectedScore*100.0)/100.0, quest.checkAnswer(anss));
    }
}
