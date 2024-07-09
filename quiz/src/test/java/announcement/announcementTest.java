package announcement;

import junit.framework.TestCase;
import models.announcement.Announcement;

import java.time.Instant;
import java.util.Date;

public class announcementTest extends TestCase {
    public void test1(){
        Date date = Date.from(Instant.now());
        Announcement announcement = new Announcement(0 , 0 , "good" , date);
        assertEquals(announcement.getId() , 0);
        assertEquals(announcement.getUserId() , 0);
        assertEquals(announcement.getText() , "good");
        assertEquals(announcement.getTimeStamp() , date);
    }

    public void test2(){
        Date date = Date.from(Instant.now());
        Announcement announcement = new Announcement(0 , 0 , "good" , date);
        announcement.setText("bad");
        announcement.setUserId(1);
        Date newDate = Date.from(Instant.now());
        announcement.setTimeStamp(newDate);
        assertEquals(announcement.getId() , 0);
        assertEquals(announcement.getUserId() , 1);
        assertEquals(announcement.getText() , "bad");
        assertEquals(announcement.getTimeStamp() , newDate);
    }
}
