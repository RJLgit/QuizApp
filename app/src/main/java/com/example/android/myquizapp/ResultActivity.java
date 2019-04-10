package com.example.android.myquizapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class ResultActivity extends AppCompatActivity {

    TextView res;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        res = findViewById(R.id.resultsTextView);
        Intent intent = getIntent();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_results);
        myToolbar.setTitle("Here is your result");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (intent.hasExtra("Username") && (intent.getStringExtra("Username") != null)) {
            mUsername = intent.getStringExtra("Username");
            ab.setSubtitle("Logged in as " + intent.getStringExtra("Username").toString());
        }
        if (intent.hasExtra("CurrentScore")) {
            boolean yourHighScore = false;
            boolean globalHighScore = false;
            String toDisplay;
            if (yourHighScore && !globalHighScore) {
                toDisplay = "Congratulations! Your score was " + intent.getIntExtra("CurrentScore", 0) + "\n" + "This is your new top score!";
            } else if (globalHighScore) {
                toDisplay = "Congratulations! Your score was " + intent.getIntExtra("CurrentScore", 0) + "\n" + "This is the highest score ever achieved!";
            } else {
                toDisplay = "Your Score was " + intent.getIntExtra("CurrentScore", 0) + "\n" + "This is not a high score.";
            }
                res.setText(toDisplay);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.top_scores_menu:
                Intent intent = new Intent(ResultActivity.this, ScoresActivity.class);
                intent.putExtra("Username", mUsername);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
