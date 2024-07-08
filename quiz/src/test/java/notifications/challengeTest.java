package notifications;

import junit.framework.TestCase;
import models.notification.Challenge;

import java.time.Instant;
import java.util.Date;

public class challengeTest extends TestCase {
    public void test1(){
        Date date = Date.from(Instant.now());
        Challenge challenge = new Challenge(0 , 0 , 1 , 1, date);
        assertEquals(challenge.getQuizId() , 1);
        challenge.setQuizId(2);
        assertEquals(challenge.getQuizId() , 2);
    }
}
