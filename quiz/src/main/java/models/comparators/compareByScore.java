package models.comparators;

import models.USER.WritenQuiz;

import java.util.Comparator;

public class compareByScore implements Comparator<WritenQuiz> {
    @Override
    public int compare(WritenQuiz writenQuiz, WritenQuiz t1) {
        return -1*(writenQuiz.getScore().compareTo(t1.getScore()));
    }
}
