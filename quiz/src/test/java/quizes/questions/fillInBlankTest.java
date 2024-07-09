package quizes.questions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.fillInBlank;

public class fillInBlankTest extends TestCase {
    public void testCheckResult(){
        JsonObject obj = new JsonObject();
        obj.addProperty("score",23);
        obj.addProperty("type","fillInBlank");
        obj.add("question",new JsonObject());
        JsonArray array = new JsonArray();
        array.add("1");
        array.add("2");
        array.add("3");
        JsonObject answer = new JsonObject();
        answer.add("answers",array);
        obj.add("answer",answer);

        Question quest = new fillInBlank(obj,1,0,0);
        String[] anss = new String[]{"1","2","3"};
        assertEquals(quest.getScore(),quest.checkAnswer(anss));
        anss[1]="3";
        anss[2]="2";
        double expectedScore = 23.0/3;
        assertEquals(Math.round(expectedScore*100.0)/100.0, quest.checkAnswer(anss));
    }

    public void test2(){
        Question quest = new fillInBlank(1,1,"dillInBlank","{}","{answers:['1','2','3']}",0,3d);
        String[] anss = new String[]{"1","2","3"};
        assertEquals(quest.getScore(),quest.checkAnswer(anss));
        anss[1]="3";
        anss[2]="2";
        double expectedScore = 3.0/3;
        assertEquals(Math.round(expectedScore*100.0)/100.0, quest.checkAnswer(anss));
    }
}
