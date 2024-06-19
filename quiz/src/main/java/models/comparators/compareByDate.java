package models.comparators;

import models.USER.WritenQuiz;

import java.util.Comparator;

public class compareByDate implements Comparator<WritenQuiz> {

    @Override
    public int compare(WritenQuiz writenQuiz, WritenQuiz t1) {
        return writenQuiz.getDate().compareTo(t1.getDate());
    }
}
