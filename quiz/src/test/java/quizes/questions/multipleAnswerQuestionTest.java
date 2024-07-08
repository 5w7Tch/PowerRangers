package quizes.questions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.multipleAnswerQuestion;

public class multipleAnswerQuestionTest extends TestCase {
    public void test1(){
        JsonObject obj = new JsonObject();
        obj.addProperty("score",23);
        obj.addProperty("type","multipleAnswerQuestion");
        obj.add("question",JsonParser.parseString("{orderMatters:true}"));
        String jsonObj = "{answers:['a','b','c']}";
        JsonObject ansObj = (JsonObject) JsonParser.parseString(jsonObj);
        obj.add("answer",ansObj);

        Question q = new multipleAnswerQuestion(obj,1,1,2);
        String[] answers = new String[]{"a","b","c"};
        double expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));

        answers = new String[]{"c","a","b"};
        expected = 0;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));

        obj.remove("question");
        obj.add("question",JsonParser.parseString("{orderMatters:false}"));
        q = new multipleAnswerQuestion(obj,1,1,2);

        answers = new String[]{"c","a","b"};
        expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));

    }

    public void test2(){
        Question q = new multipleAnswerQuestion(1,1,"multipleAnswerQuestion","{orderMatters:true}","{answers:['a','b','c']}",2,23);
        String[] answers = new String[]{"a","b","c"};
        double expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));

        answers = new String[]{"c","a","b"};
        expected = 0;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));

        q = new multipleAnswerQuestion(1,1,"multipleAnswerQuestion","{orderMatters:false}","{answers:['a','b','c']}",2,23);

        answers = new String[]{"c","a","b"};
        expected = 23;

        assertEquals(Math.round(expected*100.0)/100.0,q.checkAnswer(answers));
    }
}
