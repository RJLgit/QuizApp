package com.example.android.myquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        res = findViewById(R.id.resultsTextView);
        Intent intent = getIntent();
        if (intent.hasExtra("CurrentScore")) {
            res.setText(intent.getIntExtra("CurrentScore", 0) + "");
        }
    }
}
