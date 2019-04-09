package com.example.android.myquizapp;

import java.io.Serializable;

public class QuizQuestion implements Serializable {

    private String Question;
    private String CorrectAnswer;
    private String FalseAnswerOne;
    private String FalseAnswerTwo;
    private String FalseAnswerThree;

    public QuizQuestion() {

    }

    public QuizQuestion(String question, String correctAnswer, String falseAnswerOne, String falseAnswerTwo, String falseAnswerThree) {
        Question = question;
        CorrectAnswer = correctAnswer;
        FalseAnswerOne = falseAnswerOne;
        FalseAnswerTwo = falseAnswerTwo;
        FalseAnswerThree = falseAnswerThree;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getFalseAnswerOne() {
        return FalseAnswerOne;
    }

    public void setFalseAnswerOne(String falseAnswerOne) {
        FalseAnswerOne = falseAnswerOne;
    }

    public String getFalseAnswerTwo() {
        return FalseAnswerTwo;
    }

    public void setFalseAnswerTwo(String falseAnswerTwo) {
        FalseAnswerTwo = falseAnswerTwo;
    }

    public String getFalseAnswerThree() {
        return FalseAnswerThree;
    }

    public void setFalseAnswerThree(String falseAnswerThree) {
        FalseAnswerThree = falseAnswerThree;
    }

    @Override
    public String toString() {
        return getQuestion();
    }
}
