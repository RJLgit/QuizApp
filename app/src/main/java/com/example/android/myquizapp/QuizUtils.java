package com.example.android.myquizapp;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizUtils {

    private static final String CURRENT_SCORE_KEY = "current_score";
    private static final String GAME_FINISHED = "game_finished";

    public static void setCurrentScore(Context context, int score)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRENT_SCORE_KEY, score);
        editor.apply();
    }
    static int getCurrentScore(Context context){
        SharedPreferences mPreferences = context.getSharedPreferences(
                "score", Context.MODE_PRIVATE);
        return mPreferences.getInt(CURRENT_SCORE_KEY, 0);
    }

}
