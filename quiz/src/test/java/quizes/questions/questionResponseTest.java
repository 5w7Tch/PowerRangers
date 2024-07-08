package quizes.questions;

import com.google.gson.JsonObject;
import junit.framework.TestCase;
import models.quizes.questions.Question;
import models.quizes.questions.questionResponse;

public class questionResponseTest extends TestCase {
    public void test1(){
        Question q = new questionResponse(1,1,"questionResponse","{}","{}",0,2d);
        q.printObj();
        assertEquals(1,q.getQuestionId());
        assertEquals(1,q.getQuizId());
        assertEquals("questionResponse",q.getType());
        assertEquals("{}",q.getQuestionJson());
        assertEquals("{}",q.getAnswerJson());
        assertEquals(0,q.getOrderNum());
        assertEquals(2d,q.getScore());
    }

    public void test2(){
        JsonObject obj = new JsonObject();
        obj.addProperty("type","questionResponse");
        obj.addProperty("score",2d);
        obj.add("question",new JsonObject());
        obj.add("answer",new JsonObject());

        Question q = new questionResponse(1,1,"questionResponse","{}","{}",0,2d);

        Question q1 = new questionResponse(obj,1,1,0);
        q.printObj();
        assertEquals(q,q1);

        q1.setQuestionId(22);
        assertEquals(22,q1.getQuestionId());
        assertEquals(obj,q.generateJson());
    }

    public void testCheckResult(){
        JsonObject obj = new JsonObject();
        obj.addProperty("type","questionResponse");
        obj.addProperty("score",2d);
        JsonObject answer = new JsonObject();
        answer.addProperty("description","bla");
        obj.add("question",new JsonObject());
        obj.add("answer",answer);

        Question q = new questionResponse(obj,1,1,0);
        String[] answers = new String[]{"bla"};
        assertEquals(q.getScore(),q.checkAnswer(answers));
        answers[0]="IO";
        assertEquals(0d,q.checkAnswer(answers));
    }
}
