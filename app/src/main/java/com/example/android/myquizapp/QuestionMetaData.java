package com.example.android.myquizapp;

public class QuestionMetaData {
    private int numQuestions;

    public QuestionMetaData(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public QuestionMetaData() {
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }
}
