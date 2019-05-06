package com.example.android.myquizapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    TextView res;
    private String mUsername;
    private ProgressBar mProgressBar;
    private String uniqueUserId;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private float percentScore;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private DocumentReference globalDocumentReference;
    private DocumentReference updateGlobalReference;
    private static final int RC_SIGN_IN = 1;
    private int myScore;
    private String category;
    private int intPercentScore;
    boolean yourHighScore = false;
    boolean globalHighScore = false;
    private ShareActionProvider shareActionProvider;
    private Intent myShareIntent;
    private Button myReturnButton;
    private ImageView resultsImageView;
    private ImageView yourScoreImageView;
    private TextView descriptionOfScoreTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uniqueUserId = user.getUid();
        myReturnButton = findViewById(R.id.returnToMainActButton);
        myReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myReturnIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(myReturnIntent);
            }
        });
        resultsImageView = findViewById(R.id.resultsImageView);
        yourScoreImageView = findViewById(R.id.yourScoreImageView);
        Picasso.get().load(R.drawable.quiz_results_image).into(resultsImageView);
        Picasso.get().load(R.drawable.your_score_image).into(yourScoreImageView);

        descriptionOfScoreTextView = findViewById(R.id.isTopScoreResultsTextView);


        res = findViewById(R.id.resultsTextView);
        mProgressBar = findViewById(R.id.progressBar3);
        Intent intent = getIntent();
        category = intent.getStringExtra("Category");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_results);
        myToolbar.setTitle("Here is your result");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        myScore = intent.getIntExtra("CurrentScore", 0);

        Log.d(TAG, "onCreate: " + myScore);
        if (category.equals("Ultimate")) {
            percentScore =  ((float) myScore/ (float) QuestionActivity.totalUltimateQuestions) * 100;
        } else {
            percentScore =  ((float) myScore/ (float) QuestionActivity.totalQuestions) * 100;
        }


        Log.d(TAG, "onCreate: " + percentScore);
        intPercentScore = Math.round(percentScore);
        myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, "I got " + intPercentScore + " in " + category);

        Log.d(TAG, "onCreate: " + intPercentScore);
        globalDocumentReference = db.collection("TopScores").document(category.toLowerCase());
        documentReference = db.collection("TopScores").document(uniqueUserId);
        updateGlobalReference = db.collection("TopScores").document("globalscoreupdated");
        /*if (documentReference.get() == null) {
            TopScores nTopScores = new TopScores(0, 0,0 , 0, 0, 0, 0, 0, 0, 0);
            db.collection("TopScores").document(uniqueUserId).set(nTopScores);
        }*/
        //Dont update UI in this.
        db.runTransaction(new Transaction.Function<String>() {
            @Override
            public String apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                DocumentSnapshot myGlobalSnapshot = transaction.get(globalDocumentReference);
                String s = "";
                if (documentSnapshot.exists()) {
                    s = updateDataHelperMethod(documentSnapshot, myGlobalSnapshot, transaction);
                } else {
                    TopScores nTopScores = new TopScores(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    db.collection("TopScores").document(uniqueUserId).set(nTopScores);
                    documentSnapshot = transaction.get(documentReference);
                    s = updateDataHelperMethod(documentSnapshot, myGlobalSnapshot, transaction);
                }
                Log.d(TAG, "apply: " + s);
            return s;

            }
        }).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String toDisplay) {
                Log.d(TAG, "onSuccess: " + toDisplay);

                res.setText(intPercentScore + "%");
                descriptionOfScoreTextView.setText(toDisplay);
                res.setVisibility(View.VISIBLE);
                descriptionOfScoreTextView.setVisibility(View.VISIBLE);
                yourScoreImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ", e);
            }
        });
      //updateDatabaseScores();


        if (intent.hasExtra("Username") && (intent.getStringExtra("Username") != null)) {
            mUsername = intent.getStringExtra("Username");
            ab.setSubtitle("Logged in as " + intent.getStringExtra("Username").toString());
        }
        




        /*mDatabaseReference = mFirebaseDatabase.getReference().child(uniqueUserId);
        QuizResult qr = new QuizResult(intent.getStringExtra("Category"), intPercentScore);
        mDatabaseReference.push().setValue(qr);*/
    }

    private String updateDataHelperMethod(DocumentSnapshot documentSnapshot, DocumentSnapshot myGlobalSnapshot, Transaction transaction) {
        TopScores myRes = documentSnapshot.toObject(TopScores.class);
        final int highScore = myRes.getScoreByCategory(category);
        String toDisplay;
        GlobalScores gblScores = myGlobalSnapshot.toObject(GlobalScores.class);
        int topS = gblScores.getScore();

        if (intPercentScore > topS) {
            globalHighScore = true;
        }
        if (intPercentScore > highScore) {
            yourHighScore = true;
        }
        if (yourHighScore && !globalHighScore) {
            toDisplay = "Congratulations! This is your new top score!";
            //QuizAppWidget.newTopScore(category, intPercentScore);
            /*ComponentName componentName = new ComponentName(getApplicationContext(), QuizAppWidget.class);
            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.quiz_app_widget);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            Intent sIntent = new Intent(getApplicationContext(), QuizAppWidget.class);
            sIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetManager.getAppWidgetIds(componentName));
            sIntent.setAction(QuizAppWidget.ACTION_UPDATE_WIDGET);
            PendingIntent sPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, sIntent, 0);
            */
        } else if (globalHighScore) {
            toDisplay = "Congratulations! This is the highest score ever achieved!";
            Map<String, Object> updates = new HashMap<>();
            updates.put(category.toLowerCase(), FieldValue.serverTimestamp());
            updateGlobalReference.update(updates);
            /*QuizAppWidget.newTopScore(category, intPercentScore);*/
        } else {
            toDisplay = "Unlucky! This is not a high score.";
        }
        //So all data references are written to during the transaction
        String lastChecked = new Date().toString();
        transaction.update(documentReference, "LastTimeChecked", lastChecked);
        transaction.update(globalDocumentReference, "LastTimeChecked", lastChecked);
        if (yourHighScore) {
            Map<String, Object> upd = new HashMap<>();
            upd.put(category.toLowerCase(), intPercentScore);

            transaction.set(documentReference, upd, SetOptions.merge());
            Map<String, Object> updateDate = new HashMap<>();
            updateDate.put(category.toLowerCase() + "dateUpdated", new Date().toString());
            transaction.set(documentReference, updateDate, SetOptions.merge());
        }
        if (globalHighScore) {
            GlobalScores updGlobal = new GlobalScores(new Date().toString(), intPercentScore, mFirebaseAuth.getCurrentUser().getDisplayName());
            transaction.set(globalDocumentReference , updGlobal, SetOptions.merge());
        }
        return toDisplay;
    }

    private void updateDatabaseScores() {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    TopScores myRes = documentSnapshot.toObject(TopScores.class);
                    final int highScore = myRes.getScoreByCategory(category);
                    Log.d(TAG, "onSuccess: " + intPercentScore);
                    globalDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String toDisplay;
                                GlobalScores gblScores = documentSnapshot.toObject(GlobalScores.class);
                                int topS = gblScores.getScore();
                                if (intPercentScore > topS) {
                                    globalHighScore = true;
                                }
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
                                    Map<String, Object> upd = new HashMap<>();
                                    upd.put(category.toLowerCase(), intPercentScore);

                                    documentReference.set(upd, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ResultActivity.this, "Updated db", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                    Map<String, Object> updateDate = new HashMap<>();
                                    updateDate.put(category.toLowerCase() + "dateUpdated", new Date().toString());
                                    documentReference.set(updateDate, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                if (globalHighScore) {
                                    GlobalScores updGlobal = new GlobalScores(new Date().toString(), intPercentScore, mFirebaseAuth.getCurrentUser().getDisplayName());
                                    globalDocumentReference.set(updGlobal, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ResultActivity.this, "Updated global", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });

                } else {
                    TopScores nTopScores = new TopScores(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    db.collection("TopScores").document(uniqueUserId).set(nTopScores);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {

                                TopScores myRes = documentSnapshot.toObject(TopScores.class);
                                final int highScore = myRes.getScoreByCategory(category);
                                Log.d(TAG, "onSuccess: " + intPercentScore);
                                globalDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            String toDisplay;
                                            GlobalScores gblScores = documentSnapshot.toObject(GlobalScores.class);
                                            int topS = gblScores.getScore();
                                            if (intPercentScore > topS) {
                                                globalHighScore = true;
                                            }
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
                                                Map<String, Object> upd = new HashMap<>();
                                                upd.put(category.toLowerCase(), intPercentScore);

                                                documentReference.set(upd, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(ResultActivity.this, "Updated db", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                                Map<String, Object> updateDate = new HashMap<>();
                                                updateDate.put(category.toLowerCase() + "dateUpdated", new Date().toString());
                                                documentReference.set(updateDate, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                            if (globalHighScore) {
                                                GlobalScores updGlobal = new GlobalScores(new Date().toString(), intPercentScore, mFirebaseAuth.getCurrentUser().getDisplayName());
                                                globalDocumentReference.set(updGlobal, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(ResultActivity.this, "Updated global", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.results_menu, menu);
        MenuItem item = menu.findItem(R.id.share_menu);

        // Fetch and store ShareActionProvider
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        shareActionProvider.setShareIntent(myShareIntent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(i);
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
