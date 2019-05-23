package com.example.android.myquizapp;

import android.content.ContentResolver;
import android.content.Context;
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
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private TextView fileAddedTextView;
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
    private boolean fileAdded;
    private String isCorrectFileType;

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
        fileAddedTextView = findViewById(R.id.fileAddedTextView);
        mFirebaseAuth = FirebaseAuth.getInstance();
        questionRef = db.collection("QuizQuestions");

    }


    public void submitQuestion(View view) {
        String question = editQuestion.getText().toString().trim();
        String correct = editCorrect.getText().toString().trim();
        String falseOne = editFalseOne.getText().toString().trim();
        String falseTwo = editFalseTwo.getText().toString().trim();
        String falseThree = editFalseThree.getText().toString().trim();
        final QuizQuestion myQuestion = new QuizQuestion(question, correct, falseOne, falseTwo, falseThree);
        final String category = spinner.getSelectedItem().toString();
        if (question.equals("") || correct.equals("") || falseOne.equals("") || falseTwo.equals("") || falseThree.equals("")) {
            Toast.makeText(AddQuestionsActivity.this, "Question not added. Please enter a value for all the entries above", Toast.LENGTH_LONG).show();
            return;
        }
        if (category.equals("Music") || category.equals("Pictures")) {
            if (!fileAdded) {
                Toast.makeText(AddQuestionsActivity.this, "Please add a file before proceeding", Toast.LENGTH_LONG).show();
                return;
            }
        }

        final CollectionReference myCollRef = questionRef.document(category).collection(category + "Questions");
        final DocumentReference myDoc = questionRef.document("QuestionMetaData");
        db.runTransaction(new Transaction.Function<Object>() {

            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(myDoc);
                if (snapshot.exists()) {
                    QuestionMetaData metaData = snapshot.toObject(QuestionMetaData.class);
                    int numQues = metaData.getNumQuestions(category) + 1;

                    if (category.equals("Pictures")) {
                        Log.d(TAG, "apply: " + numQues);
                        myQuesNum = numQues;
                        StorageReference myRef = mStorageReference.child("pictures/PictureQuestion" + myQuesNum + ".JPG");
                        myRef.putFile(uri);

                        //File myFile = new File(path);
           /* StorageReference myRef = mStorageReference.child("pictures/PictureQuestion21" + ".JPG");
            UploadTask uploadTask = myRef.putFile(uri);*/
                    }
                    if (category.equals("Music")) {
                        myQuesNum = numQues;
                        StorageReference myRef = mStorageReference.child("Music/MusicQuestion" + myQuesNum + ".mp3");
                        myRef.putFile(uri);


                    }

                        transaction.update(myDoc, category, numQues);
                        DocumentReference addQuesDocRef = myCollRef.document(category + "Question" + numQues);
                        transaction.set(addQuesDocRef, myQuestion);

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
                fileAddedTextView.setText("File Not Added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Question could not be added", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.getMessage());
            }
        });

    }

    public void addFileForQuestion(View v) {
        String category = spinner.getSelectedItem().toString();
        if (category.equals("Music")) {
            showMusicFileChooser();
        } else if (category.equals("Pictures")) {
            showFileChooser();
        } else {
            Toast.makeText(AddQuestionsActivity.this, "Only add files for music and picture questions", Toast.LENGTH_LONG).show();
        }
    }

    private void showMusicFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {

            startActivityForResult(
                    Intent.createChooser(intent, "Select a .MP3 File to Upload"),
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
                    Intent.createChooser(intent, "Select a .JPG File to Upload"),
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
                    String extension = AddQuestionsActivity.getMimeType(AddQuestionsActivity.this, uri);
                    Log.d(TAG, "onActivityResult: " + extension);
                    if (extension.equals("jpg")) {
                        isCorrectFileType = "File accepted";
                        fileAddedTextView.setText(isCorrectFileType);
                        fileAdded = true;
                    } else {
                        isCorrectFileType = "File not accepted. Incorrect file type";
                        fileAddedTextView.setText(isCorrectFileType);
                    }


                   /* if (extension.equals("jpg")) {
                        StorageReference myRef = mStorageReference.child("pictures/PictureQuestion" + myQuesNum + ".JPG");
                        UploadTask uploadTask = myRef.putFile(uri);
                        try {
                            path = FileUtils.getPath(this, uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } else {
                        correctFile = false;
                        Toast.makeText(AddQuestionsActivity.this, "File type must have extension jpg. Question not added", Toast.LENGTH_LONG).show();
                    }*/
                }
                break;
            case FILE_SELECT_CODE_MUSIC:
                if (resultCode == RESULT_OK) {
                    uri = data.getData();
                    String extension = AddQuestionsActivity.getMimeType(AddQuestionsActivity.this, uri);
                    Log.d(TAG, "onActivityResult: " + extension);
                    if (extension.equals("mp3")) {
                        isCorrectFileType = "File accepted";
                        fileAddedTextView.setText(isCorrectFileType);
                        fileAdded = true;
                    } else {
                        isCorrectFileType = "File not accepted. Incorrect file type";
                        fileAddedTextView.setText(isCorrectFileType);
                    }

                   /* if (extension.equals("mp3")) {
                        StorageReference myRef = mStorageReference.child("Music/MusicQuestion" + myQuesNum + ".mp3");
                        UploadTask uploadTask = myRef.putFile(uri);
                        try {
                            path = FileUtils.getPath(this, uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } else {
                        correctFile = false;
                        Toast.makeText(AddQuestionsActivity.this, "File type must have extension mp3. Question not added", Toast.LENGTH_LONG).show();
                    }*/
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.transition.slide_in_top, R.transition.slide_out_bot);
    }


    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    public void returnToMainPage(View view) {
        Intent myReturnIntent = new Intent(AddQuestionsActivity.this, MainActivity.class);
        startActivity(myReturnIntent);
        overridePendingTransition(R.transition.slide_in_top, R.transition.slide_out_bot);
    }
}
