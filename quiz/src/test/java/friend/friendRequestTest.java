package friend;

import junit.framework.TestCase;
import models.friend.FriendRequest;

import java.time.Instant;
import java.util.Date;

public class friendRequestTest extends TestCase {
    public void test1(){
        Date curDate = Date.from(Instant.now());
        FriendRequest friendRequest = new FriendRequest(0 , 0 , 1 , curDate);
        assertEquals(friendRequest.toString() , "Note{noteId=0, fromId=0, toId=1'}");
    }
}
