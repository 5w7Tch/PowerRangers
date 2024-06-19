package models.comparators;

import models.USER.WritenQuiz;

import java.util.Comparator;

public class compareByTime implements Comparator<WritenQuiz> {

    @Override
    public int compare(WritenQuiz writenQuiz, WritenQuiz t1) {
        return writenQuiz.getTime().compareTo(t1.getTime());
    }
}
