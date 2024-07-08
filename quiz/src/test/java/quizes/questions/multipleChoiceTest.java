package quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.multipleAnswerQuestion;
import models.quizes.questions.multipleChoice;

public class multipleChoiceTest extends TestCase {
    public void test1(){
        JsonObject obj = new JsonObject();
        obj.addProperty("score",23);
        obj.addProperty("type","multipleChoice");
        obj.add("question", JsonParser.parseString("{}"));
        String jsonObj = "{answer:'a'}";
        JsonObject ansObj = (JsonObject) JsonParser.parseString(jsonObj);
        obj.add("answer",ansObj);

        Question q = new multipleChoice(obj,1,1,2);
        String[] answers = new String[]{"a"};
        double expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));


        answers = new String[]{"c"};
        expected = 0;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));

    }

    public void test2(){
        Question q = new multipleChoice(1,1,"multipleChoice","{}","{answer:'a'}",0,23);
        String[] answers = new String[]{"a"};
        double expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));


        answers = new String[]{"c"};
        expected = 0;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));
    }
}
