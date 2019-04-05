package com.example.android.myquizapp;

import java.util.ArrayList;

public class QuizQuestionClass {

    public static ArrayList<String> getCategories() {
        ArrayList<String> res = new ArrayList<>();
        res.add("Sport");
        res.add("Music");
        res.add("Nature");
        res.add("History");
        res.add("Geography");
        res.add("Technology");
        res.add("People");
        res.add("Pictures");
        res.add("Films");
        res.add("TV");
        return res;
    }
    public static ArrayList<String> getUserHighScores() {
        ArrayList<String> res = new ArrayList<>();
        res.add("65");
        res.add("54");
        res.add("76");
        res.add("88");
        res.add("23");
        res.add("46");
        res.add("87");
        res.add("56");
        res.add("67");
        res.add("78");
        return res;
    }
    public static ArrayList<String> getGlobalHighScores() {
        ArrayList<String> res = new ArrayList<>();
        res.add("12");
        res.add("34");
        res.add("56");
        res.add("67");
        res.add("78");
        res.add("89");
        res.add("93");
        res.add("19");
        res.add("53");
        res.add("34");
        return res;
    }

}