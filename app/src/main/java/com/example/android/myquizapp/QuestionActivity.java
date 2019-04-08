package com.example.android.myquizapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String REMAINING_QUESTIONS_KEY = "remaining_questions";
    private static final String CORRECT_ANSWERS_KEY = "remaining_correct_answers";
    private static final String FALSE_ANSWERS_ONE_KEY = "remaining_false_answers_one";
    private static final String FALSE_ANSWERS_TWO_KEY = "remaining_false_answers_two";
    private static final String FALSE_ANSWERS_THREE_KEY = "remaining_false_answers_three";
    private static final int CORRECT_ANSWER_DELAY_MILLIS = 1000;

    private ArrayList<String> mRemainingQuestions;
    private ArrayList<String> mCorrectAnswers;
    private ArrayList<String> mFalseAnswersOne;
    private ArrayList<String> mFalseAnswersTwo;
    private ArrayList<String> mFalseAnswersThree;
    private int mCurrentScore;
    private TextView questionTextView;
    private Button answerOne;
    private Button answerTwo;
    private Button answerThree;
    private Button answerFour;

    @Override
    public void onClick(View view) {
        showCorrectAnswer();

        Button buttonPressed = (Button) view;
        if (buttonPressed.getId() == answerOne.getId()) {
            mCurrentScore++;

        }
        QuizUtils.setCurrentScore(getApplicationContext(), mCurrentScore);
        Toast.makeText(this, "Current score is " + QuizUtils.getCurrentScore(getApplicationContext()), Toast.LENGTH_SHORT).show();
        mRemainingQuestions.remove(0);
        mFalseAnswersTwo.remove(0);
        mFalseAnswersOne.remove(0);
        mFalseAnswersThree.remove(0);
        mCorrectAnswers.remove(0);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent nextQuestionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                nextQuestionIntent.putExtra(REMAINING_QUESTIONS_KEY, mRemainingQuestions);
                nextQuestionIntent.putExtra(CORRECT_ANSWERS_KEY, mCorrectAnswers);
                nextQuestionIntent.putExtra(FALSE_ANSWERS_ONE_KEY, mFalseAnswersOne);
                nextQuestionIntent.putExtra(FALSE_ANSWERS_TWO_KEY, mFalseAnswersTwo);
                nextQuestionIntent.putExtra(FALSE_ANSWERS_THREE_KEY, mFalseAnswersThree);
                finish();
                startActivity(nextQuestionIntent);
            }
        }, CORRECT_ANSWER_DELAY_MILLIS);
    }

    private void showCorrectAnswer() {
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
            mRemainingQuestions = QuizQuestionClass.getQuizQuestions();
            mCorrectAnswers = QuizQuestionClass.getCorrectAnswers();
            mFalseAnswersOne = QuizQuestionClass.getFirstFalseAnswer();
            mFalseAnswersThree = QuizQuestionClass.getThirdFalseAnswer();
            mFalseAnswersTwo = QuizQuestionClass.getSecondFalseAnswer();
        } else {
            mRemainingQuestions = getIntent().getStringArrayListExtra(REMAINING_QUESTIONS_KEY);
            mCorrectAnswers = getIntent().getStringArrayListExtra(CORRECT_ANSWERS_KEY);
            mFalseAnswersOne = getIntent().getStringArrayListExtra(FALSE_ANSWERS_ONE_KEY);
            mFalseAnswersTwo = getIntent().getStringArrayListExtra(FALSE_ANSWERS_TWO_KEY);
            mFalseAnswersThree = getIntent().getStringArrayListExtra(FALSE_ANSWERS_THREE_KEY);
        }

        mCurrentScore = QuizUtils.getCurrentScore(getApplicationContext());

        if (mRemainingQuestions == null || mRemainingQuestions.size() < 1) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("CurrentScore", mCurrentScore);
            startActivity(intent);
        }

        questionTextView.setText(mRemainingQuestions.get(0));
        answerOne.setText(mCorrectAnswers.get(0));
        answerTwo.setText(mFalseAnswersOne.get(0));
        answerThree.setText(mFalseAnswersTwo.get(0));
        answerFour.setText(mFalseAnswersThree.get(0));
        answerOne.setOnClickListener(this);
        answerTwo.setOnClickListener(this);
        answerThree.setOnClickListener(this);
        answerFour.setOnClickListener(this);


    }
}
