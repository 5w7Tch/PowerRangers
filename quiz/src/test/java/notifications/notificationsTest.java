package notifications;

import junit.framework.TestCase;
import models.enums.*;
import models.notification.Notification;

import java.time.Instant;
import java.util.Date;

public class notificationsTest extends TestCase {
    public void test1(){
        Date curDate = Date.from(Instant.now());
        Notification notification = new Notification(0 , 0 , 1 , curDate , ActivityType.FRIEND_REQUEST);
        assertEquals(notification.getId() ,0);
        assertEquals(notification.getFromId() ,0);
        assertEquals(notification.getToId() ,1);
        assertEquals(notification.getType() ,ActivityType.FRIEND_REQUEST);
        assertEquals(notification.getSendTime() , curDate);
    }

    public void test2(){
        Date curDate = Date.from(Instant.now());
        Notification notification = new Notification(0 , 0 , 1 , curDate , ActivityType.FRIEND_REQUEST);
        notification.setId(1);
        notification.setFromId(1);
        notification.setToId(2);
        Date newCurDate = Date.from(Instant.now());
        notification.setSendTime(newCurDate);
        notification.setType(ActivityType.CHALLENGE);
        assertEquals(notification.getId() ,1);
        assertEquals(notification.getFromId() ,1);
        assertEquals(notification.getToId() ,2);
        assertEquals(notification.getType() ,ActivityType.CHALLENGE);
        assertEquals(notification.getSendTime() , newCurDate);
    }
}
