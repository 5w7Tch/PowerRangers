package quizes.questions;

import junit.framework.TestCase;
import models.quizes.questions.*;

public class QuestionTest extends TestCase {
    public void testClassManager() {
        Question.registerTypes();
        assertEquals(Question.getClass("questionResponse"), questionResponse.class);
        assertEquals(Question.getClass("fillInBlank"), fillInBlank.class);

    }
}
