package com.example.android.myquizapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URISyntaxException;

public class AddQuestionsActivity extends AppCompatActivity {
    private EditText editQuestion;
    private EditText editCorrect;
    private EditText editFalseOne;
    private EditText editFalseTwo;
    private EditText editFalseThree;
    private Spinner spinner;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef;
    private static final String TAG = "AddQuestionsActivity";
    private static final int FILE_SELECT_CODE = 0;
    private static final int FILE_SELECT_CODE_MUSIC = 1;
    Uri uri;
    String path;
    StorageReference mStorageReference;
    private int myQuesNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_add_questions);
        myToolbar.setTitle("The Ultimate Quiz App");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (getIntent().hasExtra("Username") && (getIntent().getStringExtra("Username") != null)) {
            ab.setSubtitle("Logged in as " + getIntent().getStringExtra("Username").toString());
        }
        editQuestion = findViewById(R.id.questionEntry);
        editCorrect = findViewById(R.id.correctAnswerEntry);
        editFalseOne = findViewById(R.id.firstFalseEntry);
        editFalseTwo = findViewById(R.id.secondFalseEntry);
        editFalseThree = findViewById(R.id.thirdFalseEntry);
        spinner = findViewById(R.id.spinnerAddQuestion);
        mFirebaseAuth = FirebaseAuth.getInstance();
        questionRef = db.collection("QuizQuestions");

    }


    public void submitQuestion(View view) {
        String question = editQuestion.getText().toString();
        String correct = editCorrect.getText().toString();
        String falseOne = editFalseOne.getText().toString();
        String falseTwo = editFalseTwo.getText().toString();
        String falseThree = editFalseThree.getText().toString();
        final QuizQuestion myQuestion = new QuizQuestion(question, correct, falseOne, falseTwo, falseThree);
        final String category = spinner.getSelectedItem().toString();

        final CollectionReference myCollRef = questionRef.document(category).collection(category + "Questions");
        final DocumentReference myDoc = questionRef.document("QuestionMetaData");
        db.runTransaction(new Transaction.Function<Object>() {

            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(myDoc);
                if (snapshot.exists()) {
                    QuestionMetaData metaData = snapshot.toObject(QuestionMetaData.class);
                    int numQues = metaData.getNumQuestions(category) + 1;
                    transaction.update(myDoc, category, numQues);
                    DocumentReference addQuesDocRef = myCollRef.document(category + "Question" + numQues);
                    transaction.set(addQuesDocRef, myQuestion);
                    if (category.equals("Pictures")) {
                        Log.d(TAG, "apply: " + numQues);
                        myQuesNum = numQues;
                        showFileChooser();
                        //File myFile = new File(path);
           /* StorageReference myRef = mStorageReference.child("pictures/PictureQuestion21" + ".JPG");
            UploadTask uploadTask = myRef.putFile(uri);*/
                    }
                    if (category.equals("Music")) {
                        myQuesNum = numQues;
                        showMusicFileChooser();
                    }
                }
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getApplicationContext(), "Question added", Toast.LENGTH_SHORT).show();
                editQuestion.getText().clear();
                editCorrect.getText().clear();
                editFalseOne.getText().clear();
                editFalseTwo.getText().clear();
                editFalseThree.getText().clear();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Question could not be added", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.getMessage());
            }
        });

    }

    private void showMusicFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {

            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE_MUSIC);

        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {

            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);

        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {



                    uri = data.getData();
                    StorageReference myRef = mStorageReference.child("pictures/PictureQuestion" + myQuesNum + ".JPG");
                    UploadTask uploadTask = myRef.putFile(uri);
                    try {
                        path = FileUtils.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case FILE_SELECT_CODE_MUSIC:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    StorageReference myRef = mStorageReference.child("Music/MusicQuestion" + myQuesNum + ".mp3");
                    UploadTask uploadTask = myRef.putFile(uri);
                    try {
                        path = FileUtils.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
