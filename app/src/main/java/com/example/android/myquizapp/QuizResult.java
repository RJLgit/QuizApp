package com.example.android.myquizapp;

public class QuizResult {

    private String quizRound;
    private int score;


    public QuizResult(String n, int s) {
        quizRound = n;
        score = s;
    }

    public String getQuizRound() {
        return quizRound;
    }

    public void setQuizRound(String quizRound) {
        this.quizRound = quizRound;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
