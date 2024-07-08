package notifications;

import junit.framework.TestCase;
import models.notification.Note;

import java.time.Instant;
import java.util.Date;

public class noteTests extends TestCase {
    public void test1(){
        Date curDate = Date.from(Instant.now());
        Note note = new Note(0 , 0 , 1 , "blab" , curDate);
        assertEquals(note.getText() , "blab");
        note.setText("blaa");
        assertEquals(note.getText() , "blaa");
    }

    public void test2(){
        Date curDate = Date.from(Instant.now());
        Note note = new Note(0 , 0 , 1 , "blab" , curDate);
        assertEquals(note.toString() , "Note{noteId=0, fromId=0, toId=1, text='blab'}");
    }
}
