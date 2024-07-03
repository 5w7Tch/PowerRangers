package models.note;

import models.note.abstractions.INote;

public class Note implements INote
{
    private int noteId;
    private int fromId;
    private int toId;
    private String text;

    public Note(int noteId, int fromId, int toId, String text) {
        this.noteId = noteId;
        this.fromId = fromId;
        this.toId = toId;
        this.text = text;
    }

    @Override
    public int getNoteId() {
        return noteId;
    }

    @Override
    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @Override
    public int getFromId() {
        return fromId;
    }

    @Override
    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    @Override
    public int getToId() {
        return toId;
    }

    @Override
    public void setToId(int toId) {
        this.toId = toId;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", text='" + text + '\'' +
                '}';
    }
}
