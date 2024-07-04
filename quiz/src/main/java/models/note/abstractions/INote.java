package models.note.abstractions;

public interface INote
{
    int getNoteId();
    void setNoteId(int noteId);

    int getFromId();
    void setFromId(int fromId);

    int getToId();
    void setToId(int toId);

    String getText();
    void setText(String text);
}
