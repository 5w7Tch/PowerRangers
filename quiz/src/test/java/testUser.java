import junit.framework.TestCase;
import models.USER.Hasher;
import models.USER.User;


public class testUser extends TestCase {

    public void testUser1(){
        User user = new User(1,"bla","bla123","bla@gmal.com",true);
        assertEquals(1,user.getId());
        assertEquals("bla",user.getUsername());
        assertEquals(Hasher.getPasswordHash("bla123"),user.getPasswordHash());
        assertEquals("bla@gmal.com",user.getEmail());
        assertTrue(user.isAdmin());
    }

    public void testUser2(){
        User user = new User(1,"bla","bla123","bla@gmal.com",true);
        assertEquals(1,user.getId());
        assertEquals("bla",user.getUsername());
        assertEquals(Hasher.getPasswordHash("bla123"),user.getPasswordHash());
        assertEquals("bla@gmal.com",user.getEmail());
        assertTrue(user.isAdmin());
        user.setId(12);
        assertEquals(12,user.getId());
    }

}
