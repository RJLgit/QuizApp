package com.example.android.myquizapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

private static final String REMAINING_QUESTIONS_KEY = "remaining_questions";
    private static final String CURRENT_QUESTION_KEY = "current_question";
    private static final String QUESTIONS_TO_ASK_KEY = "all_questions";
private static final int CORRECT_ANSWER_DELAY_MILLIS = 1000;
private static final String TAG = "QuestionActivity";

    private int mCurrentScore;
    private TextView questionTextView;
    private Button answerOne;
    private Button answerTwo;
    private Button answerThree;
    private Button answerFour;
    private QuizQuestion currentQuestion;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myRef = db.collection("QuizQuestions").document("Sport").collection("SportQuestions");
    public static int totalQuestions = 5;
    private int currentQuestionIndex;
    private int questionsLeft;
    private ArrayList<Integer> questionsToAsk;


    @Override
    public void onClick(View view) {
        answerOne.setEnabled(false);
        answerTwo.setEnabled(false);
        answerThree.setEnabled(false);
        answerFour.setEnabled(false);
        showCorrectAnswer();

        Button buttonPressed = (Button) view;
        if (buttonPressed.getText().equals(currentQuestion.getCorrectAnswer())) {
            mCurrentScore++;

        }
        QuizUtils.setCurrentScore(getApplicationContext(), mCurrentScore);
        Toast.makeText(this, "Current score is " + QuizUtils.getCurrentScore(getApplicationContext()), Toast.LENGTH_SHORT).show();
        questionsLeft--;
        currentQuestionIndex++;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent nextQuestionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                nextQuestionIntent.putExtra(QUESTIONS_TO_ASK_KEY, questionsToAsk);
                nextQuestionIntent.putExtra(REMAINING_QUESTIONS_KEY, questionsLeft);
                nextQuestionIntent.putExtra(CURRENT_QUESTION_KEY, currentQuestionIndex);
                finish();
                startActivity(nextQuestionIntent);
            }
        }, CORRECT_ANSWER_DELAY_MILLIS);
    }

    private void showCorrectAnswer() {

        if (answerOne.getText().equals(currentQuestion.getCorrectAnswer())) {
            answerOne.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_green_light),
                    PorterDuff.Mode.MULTIPLY);
            answerOne.setTextColor(Color.WHITE);
            answerTwo.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerTwo.setTextColor(Color.WHITE);
            answerThree.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerThree.setTextColor(Color.WHITE);
            answerFour.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerFour.setTextColor(Color.WHITE);
        } else if (answerTwo.getText().equals(currentQuestion.getCorrectAnswer())) {
            answerTwo.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_green_light),
                    PorterDuff.Mode.MULTIPLY);
            answerTwo.setTextColor(Color.WHITE);
            answerOne.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerOne.setTextColor(Color.WHITE);
            answerThree.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerThree.setTextColor(Color.WHITE);
            answerFour.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerFour.setTextColor(Color.WHITE);
        } else if (answerThree.getText().equals(currentQuestion.getCorrectAnswer())) {
            answerThree.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_green_light),
                    PorterDuff.Mode.MULTIPLY);
            answerThree.setTextColor(Color.WHITE);
            answerTwo.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerTwo.setTextColor(Color.WHITE);
            answerOne.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerOne.setTextColor(Color.WHITE);
            answerFour.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerFour.setTextColor(Color.WHITE);
        } else if (answerFour.getText().equals(currentQuestion.getCorrectAnswer())) {
            answerFour.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_green_light),
                    PorterDuff.Mode.MULTIPLY);
            answerFour.setTextColor(Color.WHITE);
            answerTwo.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerTwo.setTextColor(Color.WHITE);
            answerThree.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerThree.setTextColor(Color.WHITE);
            answerOne.getBackground().setColorFilter(ContextCompat.getColor
                            (this, android.R.color.holo_red_light),
                    PorterDuff.Mode.MULTIPLY);
            answerOne.setTextColor(Color.WHITE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionTextView = findViewById(R.id.questionView);
        answerOne = findViewById(R.id.buttonA);
        answerTwo = findViewById(R.id.buttonD);
        answerThree = findViewById(R.id.buttonB);
        answerFour = findViewById(R.id.buttonC);

        boolean isNewGame = !getIntent().hasExtra(REMAINING_QUESTIONS_KEY);

        if (isNewGame) {
            QuizUtils.setCurrentScore(getApplicationContext(), 0);
            questionsToAsk = generateQuestionsToAsk();
            currentQuestionIndex = 0;
            questionsLeft = QuestionActivity.totalQuestions;


        } else {
            currentQuestionIndex = getIntent().getIntExtra(CURRENT_QUESTION_KEY, 1);
            questionsLeft = getIntent().getIntExtra(REMAINING_QUESTIONS_KEY, 0);
            questionsToAsk = getIntent().getIntegerArrayListExtra(QUESTIONS_TO_ASK_KEY);
        }

        mCurrentScore = QuizUtils.getCurrentScore(getApplicationContext());

        if (questionsLeft == 0) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("CurrentScore", mCurrentScore);
            startActivity(intent);
        } else {
            myRef.document("SportQuestion" + questionsToAsk.get(currentQuestionIndex)).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                currentQuestion = documentSnapshot.toObject(QuizQuestion.class);
                                questionTextView.setText(currentQuestion.getQuestion());
                                ArrayList<String> mAnswers = initAnswers(currentQuestion);
                                answerOne.setText(mAnswers.get(0));
                                answerTwo.setText(mAnswers.get(1));
                                answerThree.setText(mAnswers.get(2));
                                answerFour.setText(mAnswers.get(3));

                                answerOne.setOnClickListener(QuestionActivity.this);
                                answerTwo.setOnClickListener(QuestionActivity.this);
                                answerThree.setOnClickListener(QuestionActivity.this);
                                answerFour.setOnClickListener(QuestionActivity.this);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: faileddd");
                        }
                    });



        }
    }

    private ArrayList<Integer> generateQuestionsToAsk() {
        ArrayList<Integer> results = new ArrayList<>();

        while (results.size() < totalQuestions) {
            Random rn = new Random();
            int answer = rn.nextInt(10) + 1;
            Integer myInt = new Integer(answer);
            if (!results.contains(myInt)) {
                results.add(myInt);
            }
        }
        return results;
    }

    private ArrayList<String> initAnswers(QuizQuestion question) {
        ArrayList<String> answers = new ArrayList<String>();
        answers.add(question.getCorrectAnswer());
        answers.add(question.getFalseAnswerOne());
        answers.add(question.getFalseAnswerTwo());
        answers.add(question.getFalseAnswerThree());
        Collections.shuffle(answers);
        return answers;

    }

}

