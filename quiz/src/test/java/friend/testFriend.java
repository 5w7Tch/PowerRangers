package friend;

import junit.framework.TestCase;
import models.friend.Friend;

import java.sql.Date;

public class testFriend extends TestCase {
    public void test1(){
        Friend friend = new Friend(0 , 0 , 1,new Date(System.currentTimeMillis()));
        assertEquals(friend.getId() , 0);
        assertEquals(friend.getUserOneId() , 0);
        assertEquals(friend.getUserTwoId() , 1);
    }

    public void test2(){
        Friend friend = new Friend(0,0 , 1,new Date(System.currentTimeMillis()));
        friend.setUserOneId(1);
        friend.setUserTwoId(2);
        friend.setId(1);
        assertEquals(friend.getUserOneId() , 1);
        assertEquals(friend.getUserTwoId() , 2);
        assertEquals(friend.getId() , 1);
    }
}
