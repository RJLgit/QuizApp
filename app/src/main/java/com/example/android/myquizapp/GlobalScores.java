package com.example.android.myquizapp;

import com.google.firebase.firestore.Exclude;

public class GlobalScores {
    private String date;
    private int score;
    private String user;
    private String category;

    public GlobalScores(String date, int score, String user) {
        this.date = date;
        this.score = score;
        this.user = user;

    }

    public GlobalScores() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    @Exclude
    public String getCategory() {
        return category;
    }
    @Exclude
    public void setCategory(String category) {
        this.category = category;
    }
}
