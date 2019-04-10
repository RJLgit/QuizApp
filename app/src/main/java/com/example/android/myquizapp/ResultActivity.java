package com.example.android.myquizapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    TextView res;
    private String mUsername;
    private String uniqueUserId;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private float percentScore;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private static final int RC_SIGN_IN = 1;
    private int myScore;
    private String category;
    private int intPercentScore;
    boolean yourHighScore = false;
    boolean globalHighScore = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uniqueUserId = user.getUid();

        res = findViewById(R.id.resultsTextView);
        Intent intent = getIntent();
        category = intent.getStringExtra("Category");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_results);
        myToolbar.setTitle("Here is your result");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        myScore = intent.getIntExtra("CurrentScore", 0);

        Log.d(TAG, "onCreate: " + myScore);
        percentScore =  ((float) myScore/ (float) QuestionActivity.totalQuestions) * 100;
        Log.d(TAG, "onCreate: " + percentScore);
        intPercentScore = Math.round(percentScore);
        Log.d(TAG, "onCreate: " + intPercentScore);

        documentReference = db.collection("TopScores").document(uniqueUserId);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String toDisplay;
                    TopScores myRes = documentSnapshot.toObject(TopScores.class);
                    int highScore = myRes.getScoreByCategory(category);
                    Log.d(TAG, "onSuccess: " + intPercentScore);
                    if (intPercentScore > highScore) {
                        yourHighScore = true;
                    }
                    if (yourHighScore && !globalHighScore) {
                        toDisplay = "Congratulations! Your score was " + intPercentScore + " percent!" + "\n" + "This is your new top score!";

                    } else if (globalHighScore) {
                        toDisplay = "Congratulations! Your score was " + intPercentScore + " percent!" + "\n" + "This is the highest score ever achieved!";
                    } else {
                        toDisplay = "Your Score was " + intPercentScore + " percent!" + "\n" + "This is not a high score.";
                    }
                    res.setText(toDisplay);
                    if (yourHighScore) {
                        myRes.setScoreByCategory(category, intPercentScore);
                        documentReference.set(myRes).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ResultActivity.this, "Updated db", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        if (intent.hasExtra("Username") && (intent.getStringExtra("Username") != null)) {
            mUsername = intent.getStringExtra("Username");
            ab.setSubtitle("Logged in as " + intent.getStringExtra("Username").toString());
        }
        




        /*mDatabaseReference = mFirebaseDatabase.getReference().child(uniqueUserId);
        QuizResult qr = new QuizResult(intent.getStringExtra("Category"), intPercentScore);
        mDatabaseReference.push().setValue(qr);*/
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
