package models.quizes;

import com.google.gson.JsonObject;
import models.activity.Activity;
import models.enums.ActivityType;

import java.sql.Date;

public class Quiz extends Activity
{
    private int id;
    private int author;
    private String name;
    private Date creationDate;
    private String description;
    private boolean isPracticable;
    private boolean isRandomQuestionSec;
    private double duration;
    private boolean immediateCorrection;

    public Quiz(int id, int author, String name, Date creationDate, String description, boolean isPracticable, boolean rand, double duration,boolean immediateCorrection) {
        super(id, author, creationDate, ActivityType.CREATED_QUIZ);
        this.id = id;
        this.author = author;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
        this.isPracticable = isPracticable;
        this.duration = duration;
        this.isRandomQuestionSec = rand;
        this.immediateCorrection=immediateCorrection;
    }

    public int getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPracticable() {
        return isPracticable;
    }

    public boolean isQuestionSecRand() {
        return isRandomQuestionSec;
    }

    public double getDuration() {
        return duration;
    }

    public boolean isImmediateCorrection() {
        return immediateCorrection;
    }

    public JsonObject getJson(){
        JsonObject obj = new JsonObject();
        obj.addProperty("quizId",id);
        obj.addProperty("author",author);
        obj.addProperty("title",name);
        obj.addProperty("creationDate",creationDate.toString());
        obj.addProperty("description",description);
        obj.addProperty("isPracticable",isPracticable);
        obj.addProperty("randomSeq",isRandomQuestionSec);
        obj.addProperty("duration",duration);
        obj.addProperty("immediateCorrection",immediateCorrection);

        return obj;
    }
}
