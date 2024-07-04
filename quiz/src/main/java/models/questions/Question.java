package models.questions;

public interface Question {
    enum questionTypes{
        questionResponse,
        fillInTheBlank,
        multipleChoice,
        pictureResponseQuestion,
        multipleAnswerQuestions,
        multipleChoiceWithMultipleAnswers,
        matching
    }
    String getQuestion();
    Double checkAnswer(String[] answers);
    int getType();
    Double getScore();

}
