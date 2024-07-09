package models.notification;

import models.enums.ActivityType;
import models.notification.abstractions.INote;

import java.util.Date;

public class Note extends Notification implements INote
{
    private String text;

    public Note(int noteId, int fromId, int toId, String text, Date sendTime) {
        super(noteId, fromId, toId, sendTime, ActivityType.NOTE);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + super.getId() +
                ", fromId=" + super.getFromId() +
                ", toId=" + super.getToId() +
                ", text='" + text + '\'' +
                '}';
    }
}
