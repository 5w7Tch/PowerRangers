package models.USER;

import java.util.Date;

public class Quiz {
    private int id;
    private int author;
    private String name;
    private Date creationDate;
    private String description;
    private boolean isPracticable;
    private boolean isRandomQuestionSec;
    private double duration;

    public Quiz(int id, int author, String name, Date creationDate, String description, boolean isPracticable, boolean rand, double duration) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.creationDate = creationDate;
        this.description = description;
        this.isPracticable = isPracticable;
        this.duration = duration;
        this.isRandomQuestionSec = rand;
    }

    public int getId() {
        return id;
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
}
