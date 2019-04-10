package com.example.android.myquizapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {

    TextView res;
    private String mUsername;
    private String uniqueUserId;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private float percentScore;
    private static final int RC_SIGN_IN = 1;

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

        percentScore = (intent.getIntExtra("CurrentScore", 0)/QuestionActivity.totalQuestions) * 100;
        int intPercentScore = Math.round(percentScore);

        if (intent.hasExtra("Username") && (intent.getStringExtra("Username") != null)) {
            mUsername = intent.getStringExtra("Username");
            ab.setSubtitle("Logged in as " + intent.getStringExtra("Username").toString());
        }
        if (intent.hasExtra("CurrentScore")) {
            boolean yourHighScore = false;
            boolean globalHighScore = false;
            String toDisplay;
            if (yourHighScore && !globalHighScore) {
                toDisplay = "Congratulations! Your score was " + intPercentScore + " percent!" + "\n" + "This is your new top score!";
            } else if (globalHighScore) {
                toDisplay = "Congratulations! Your score was " + intPercentScore + " percent!" + "\n" + "This is the highest score ever achieved!";
            } else {
                toDisplay = "Your Score was " + intPercentScore + " percent!" + "\n" + "This is not a high score.";
            }
                res.setText(toDisplay);
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uniqueUserId = user.getUid();



        mDatabaseReference = mFirebaseDatabase.getReference().child(uniqueUserId);
        QuizResult qr = new QuizResult(intent.getStringExtra("Category"), intPercentScore);
        mDatabaseReference.push().setValue(qr);
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
