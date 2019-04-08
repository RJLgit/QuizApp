package com.example.android.myquizapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class GlobalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_score);

        GlobalScoreFragment globalScoreFragment = new GlobalScoreFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.globalScoresFragmentContainer, globalScoreFragment).commit();
        Intent i = getIntent();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_global_scores);
        if (i.hasExtra("Category")) {
            myToolbar.setTitle("Here is the top global score for " + i.getStringExtra("Category"));
        }
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        if (i.hasExtra("Username")) {
            ab.setSubtitle("Logged in as " + i.getStringExtra("Username").toString());
        }

    }
}
