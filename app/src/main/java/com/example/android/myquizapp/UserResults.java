package com.example.android.myquizapp;


import java.util.ArrayList;

public class UserResults {
    private static UserResults single_user_results;
    public ArrayList<String> questions;
    public ArrayList<String> userAnswers;
    public ArrayList<String> correctAnswers;

    private UserResults() {
        questions = new ArrayList<>();
        userAnswers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
    }
    public static UserResults getInstance() {
        if (single_user_results == null) {
            single_user_results = new UserResults();
        }
        return single_user_results;
    }
    public void clear() {
        if (questions != null) {
            questions.clear();
        }
        if (userAnswers != null) {
            userAnswers.clear();
        }
        if (correctAnswers != null) {
            correctAnswers.clear();
        }
    }

}
