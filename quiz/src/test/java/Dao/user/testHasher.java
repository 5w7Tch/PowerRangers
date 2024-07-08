package Dao.user;

import junit.framework.TestCase;
import models.USER.Hasher;

public class testHasher extends TestCase {
    public void testHasher1(){
        String word = "blabla";
        for(int i=0;i<50;i++){
            assertEquals(Hasher.getPasswordHash(word),Hasher.getPasswordHash(word));
        }
    }

}
