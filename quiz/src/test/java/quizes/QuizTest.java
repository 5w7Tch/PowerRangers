package quizes;

import com.google.gson.JsonObject;
import junit.framework.TestCase;
import models.quizes.Quiz;

import java.sql.Date;

public class QuizTest extends TestCase {
    public void testQuiz1(){
        Quiz quiz = new Quiz(1,1,"math",new Date(3),"bla",true,true,50,false);
        assertEquals(1,quiz.getId());
        assertEquals(1,quiz.getAuthor());
        assertEquals("math",quiz.getName());
        assertEquals("bla",quiz.getDescription());
        assertEquals(50d,quiz.getDuration());
        assertTrue(quiz.isPracticable());
        assertTrue(quiz.isQuestionSecRand());
        assertFalse(quiz.isImmediateCorrection());
        assertEquals(new Date(3),quiz.getCreationDate());

        quiz.setId(3);
        assertEquals(3, quiz.getId());
    }

    public void testGetJson(){
        Quiz quiz = new Quiz(1,1,"math",new Date(3),"bla",true,true,50,false);

        JsonObject obj = new JsonObject();
        obj.addProperty("quizId",1);
        obj.addProperty("author",1);
        obj.addProperty("title","math");
        obj.addProperty("creationDate",new Date(3).toString());
        obj.addProperty("description","bla");
        obj.addProperty("isPracticable",true);
        obj.addProperty("randomSeq",true);
        obj.addProperty("duration",50d);
        obj.addProperty("immediateCorrection",false);

        assertEquals(quiz.getJson(),obj);


    }

}
