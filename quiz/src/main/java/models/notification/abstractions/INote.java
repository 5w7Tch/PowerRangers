package models.notification.abstractions;

public interface INote extends INotification
{
    String getText();
    void setText(String text);
}
