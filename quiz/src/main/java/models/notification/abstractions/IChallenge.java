package models.notification.abstractions;

public interface IChallenge extends INotification
{
    int getQuizId();
    void setQuizId(int id);
}
