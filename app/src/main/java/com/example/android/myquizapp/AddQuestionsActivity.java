package com.example.android.myquizapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
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

                }
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getApplicationContext(), "Question added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Question could not be added", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.getMessage());
            }
        });

    }
}
