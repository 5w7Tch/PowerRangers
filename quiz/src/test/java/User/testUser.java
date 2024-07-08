package User;

import junit.framework.TestCase;
import models.USER.Hasher;
import models.USER.User;

import java.util.Optional;

import static org.junit.Assert.assertNotEquals;


public class testUser extends TestCase {

    public void testUser1(){
        User user = new User(1,"bla","bla123","bla@gmal.com",true);
        assertEquals(1,user.getId().intValue());
        assertEquals("bla",user.getUsername());
        assertEquals(Hasher.getPasswordHash("bla123"),user.getPasswordHash());
        assertEquals("bla@gmal.com",user.getEmail());
        assertTrue(user.isAdmin());
        user.setHash("bla");
        assertEquals("bla",user.getPasswordHash());
    }

    public void testUser2(){
        User user = new User(1,"bla","bla123","bla@gmal.com",true);
        assertEquals(1,user.getId().intValue());
        assertEquals("bla",user.getUsername());
        assertEquals(Hasher.getPasswordHash("bla123"),user.getPasswordHash());
        assertEquals("bla@gmal.com",user.getEmail());
        assertTrue(user.isAdmin());
        user.setId(12);
        assertEquals(12,user.getId().intValue());
    }

    public void testEquals(){
        User user1 = new User(1,"bla","bla123","bla@gmal.com",true);
        User user2 = new User(1,"bla","bla123","bla@gmal.com",true);

        assertEquals(user1,user2);
        System.out.println(user1);
        System.out.println(user2);
        user2.setId(-2);
        assertNotEquals(user1, user2);
    }

}
