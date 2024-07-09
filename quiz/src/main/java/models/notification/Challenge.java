package models.notification;

import models.enums.ActivityType;
import models.notification.abstractions.IChallenge;

import java.util.Date;

public class Challenge extends Notification implements IChallenge
{
    private int quizId;
    public Challenge(int id, int fromId, int toId, int quizId, Date sendTime)
    {
        super(id, fromId, toId, sendTime, ActivityType.CHALLENGE);
        this.quizId = quizId;
    }

    @Override
    public int getQuizId()
    {
        return this.quizId;
    }

    @Override
    public void setQuizId(int id)
    {
        this.quizId = id;
    }
}
