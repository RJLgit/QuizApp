package com.example.android.myquizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.ListItemClickListener {
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mListener;
    private String mUsername;
    private String uniqueUserId;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
   private FirebaseDatabase mFirebaseDatabase;
   private DatabaseReference mDatabaseReference;
   private StorageReference mStorageRef;
    private static final String TAG = "MainActivity";
    private Button testButt;

    //Sign in Request code
    private static final int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testButt = findViewById(R.id.test_not_but);
        testButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationUtils.updateUserAboutTopScores(MainActivity.this);
            }
        });
        mUsername = "ANON";


        progressBar = findViewById(R.id.mainProgressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mCategoryAdapter = new CategoryAdapter(MainActivity.this, QuizQuestionClass.getCategories(), QuizQuestionClass.getUserHighScores(), QuizQuestionClass.getGlobalHighScores(), this);
        mRecyclerView.setAdapter(mCategoryAdapter);

        mStorageRef = FirebaseStorage.getInstance().getReference();
       mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    uniqueUserId = user.getUid();
                    onSignedInInit(user.getDisplayName());
                    updateActionBar();
                } else {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build(), new AuthUI.IdpConfig.EmailBuilder().build()))
                            .setIsSmartLockEnabled(false).build(), RC_SIGN_IN);
                    if (user != null) {
                        uniqueUserId = user.getUid();
                        onSignedInInit(user.getDisplayName());
                        updateActionBar();
                    }
                }
            }
        };

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("The Ultimate Quiz App");
        setSupportActionBar(myToolbar);

        NotificationUtils.scheduleNotificationUpdate(this);
       //batchWriteToAddQuestions();
        //addPicsToStorage();
    }

    private void addPicsToStorage() {
        Uri file = Uri.fromFile(new File("/QuizImages/PictureQuestion1.jpg"));
        StorageReference picRef = mStorageRef.child("pictures/PictureQuestion1.jpg");
        picRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri dlUri = taskSnapshot.getUploadSessionUri();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ");


            }
        });
    }

    @Override
    public void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }


    private void batchWriteToAddQuestions() {
        WriteBatch batch = db.batch();
        String batchCategory = "TV";
        ArrayList<QuizQuestion> lst = QuizQuestionClass.getTVQuestions();
        for (int i = 1; i < 6; i++) {
            DocumentReference myRef = db.collection("QuizQuestions")
                    .document(batchCategory).collection(batchCategory + "Questions")
                    .document(batchCategory + "Question" + i);
            batch.set(myRef, lst.get(i - 1));
        }
        batch.commit();
    }


    void updateActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setSubtitle("Logged in as " + mUsername);
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
                Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
                intent.putExtra("Username", mUsername);
                startActivity(intent);
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

    //helper method to initialize top scores
    protected void initTopScores() {

    }

    //Helper methods to help sign in
    protected void onSignedInInit(String username) {
        mUsername = username;
    }

    protected void onSignedOutInit() {
        mUsername = "ANON";
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mFirebaseAuth.removeAuthStateListener(mListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mListener);
    }

    @Override
    public void onListItemCLick(String cat) {
        Intent i = new Intent(this, QuestionActivity.class);
        i.putExtra("CategoryClicked", cat);
        i.putExtra("Username", mUsername);
        startActivity(i);
        Toast.makeText(this, "Category clicked " + cat, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(MainActivity.this, "Welcome, you are logged in " + mUsername, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Sign in cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
