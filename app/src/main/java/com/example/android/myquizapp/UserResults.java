package com.example.android.myquizapp;


import java.util.ArrayList;

public class UserResults {
    private static UserResults single_user_results;
    public ArrayList<String> questions;
    public ArrayList<String> userAnswers;
    public ArrayList<String> correctAnswers;
    public ArrayList<String> categories;
    public ArrayList<Integer> questionIds;

    private UserResults() {
        questions = new ArrayList<>();
        userAnswers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        categories = new ArrayList<>();
        questionIds = new ArrayList<>();
    }
    public static UserResults getInstance() {
        if (single_user_results == null) {
            single_user_results = new UserResults();
        }
        return single_user_results;
    }
    public void clear() {
        if (categories != null) {
            categories.clear();
        }
        if (questionIds != null) {
            questionIds.clear();
        }
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

    public static UserResults getSingle_user_results() {
        return single_user_results;
    }

    public static void setSingle_user_results(UserResults single_user_results) {
        UserResults.single_user_results = single_user_results;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(ArrayList<String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<Integer> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(ArrayList<Integer> questionIds) {
        this.questionIds = questionIds;
    }
}
