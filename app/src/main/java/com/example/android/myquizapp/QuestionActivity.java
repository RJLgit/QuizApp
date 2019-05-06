package com.example.android.myquizapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, ExoPlayer.EventListener {

private static final String REMAINING_QUESTIONS_KEY = "remaining_questions";
    private static final String CURRENT_QUESTION_KEY = "current_question";
    private static final String QUESTIONS_TO_ASK_KEY = "all_questions";
private static final int CORRECT_ANSWER_DELAY_MILLIS = 1000;
private static final String TAG = QuestionActivity.class.getSimpleName();
private String category;

    private int mCurrentScore;
    private TextView questionTextView;
    private ImageView questionImageView;
    private Button answerOne;
    private Button answerTwo;
    private Button answerThree;
    private Button answerFour;
    private QuizQuestion currentQuestion;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myRef;
    public static int totalQuestions = 5;
    public static int totalUltimateQuestions = 20;
    private int currentQuestionIndex;
    private int questionsLeft;
    private ArrayList<Integer> questionsToAsk;
    private String mUsername;
    private TextView pictureQuestionTextView;
    StorageReference mStorageReference;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private FirebaseAuth mFirebaseAuth;
    private Boolean isUltimate;
    public boolean isInBackground;

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();

            mExoPlayer = null;
        }

            if (mMediaSession != null) {
                mMediaSession.setActive(false);
            }

    }

/*    @Override
    protected void onStop() {
        super.onStop();
        mExoPlayer.release();
        mExoPlayer = null;
    }*/

    @Override
    protected void onDestroy() {

        super.onDestroy();
        isInBackground = true;
        releasePlayer();



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
                Intent i = new Intent(QuestionActivity.this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.top_scores_menu:
                Intent intent = new Intent(QuestionActivity.this, ScoresActivity.class);
                intent.putExtra("Username", mUsername);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
                nextQuestionIntent.putExtra("CategoryClicked", category);
                nextQuestionIntent.putExtra("Username", mUsername);
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
        isInBackground = false;
        setContentView(R.layout.activity_question);
        questionTextView = findViewById(R.id.questionView);
        questionImageView = findViewById(R.id.imageView);
        pictureQuestionTextView = findViewById(R.id.pictureQuestionTextView);
        mPlayerView = findViewById(R.id.playerView);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        answerOne = findViewById(R.id.buttonA);
        answerTwo = findViewById(R.id.buttonD);
        answerThree = findViewById(R.id.buttonB);
        answerFour = findViewById(R.id.buttonC);
        boolean isNewGame = !getIntent().hasExtra(REMAINING_QUESTIONS_KEY);

        questionTextView.setBackgroundResource(R.drawable.question_mark);

        questionTextView.getBackground().setAlpha(10);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_question);
        myToolbar.setTitle("Answer this question");
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (getIntent().hasExtra("Username") && (getIntent().getStringExtra("Username") != null)) {
            mUsername = getIntent().getStringExtra("Username");
            ab.setSubtitle("Logged in as " + getIntent().getStringExtra("Username").toString());
        } else {
            mFirebaseAuth = FirebaseAuth.getInstance();
            if (mFirebaseAuth.getCurrentUser() != null) {
                mUsername = mFirebaseAuth.getCurrentUser().getDisplayName();
                ab.setSubtitle("Logged in as " + mUsername);
            } else {
                Intent startMainActIntent = new Intent(this, MainActivity.class);
                startActivity(startMainActIntent);
            }
        }

        category = getIntent().getStringExtra("CategoryClicked");
        if (category.equals("Pictures")) {
            questionTextView.setVisibility(View.INVISIBLE);
            questionImageView.setVisibility(View.VISIBLE);
            pictureQuestionTextView.setVisibility(View.VISIBLE);


        }
        if (category.equals("Music")) {
            questionTextView.setVisibility(View.INVISIBLE);
            pictureQuestionTextView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                    getResources(), R.drawable.question_mark
            ));
            pictureQuestionTextView.setText("Who wrote this music?");
        }
        if (category.equals("Ultimate")) {
            isUltimate = true;
            if (isNewGame) {
                QuizUtils.setCurrentScore(getApplicationContext(), 0);

                currentQuestionIndex = 0;
                questionsLeft = QuestionActivity.totalUltimateQuestions;

                questionsToAsk = generateUltimateQuestionsToAsk();
                myToolbar.setTitle("Question " + (currentQuestionIndex + 1) + " of " + (questionsLeft + currentQuestionIndex));

            } else {
                currentQuestionIndex = getIntent().getIntExtra(CURRENT_QUESTION_KEY, 1);
                questionsLeft = getIntent().getIntExtra(REMAINING_QUESTIONS_KEY, 0);
                questionsToAsk = getIntent().getIntegerArrayListExtra(QUESTIONS_TO_ASK_KEY);
                myToolbar.setTitle("Question " + (currentQuestionIndex + 1) + " of " + (questionsLeft + currentQuestionIndex));
            }

            String ultimateCategory = getUltimateCategory(questionsLeft);
            Log.d(TAG, "onCreate: " + ultimateCategory);
            mCurrentScore = QuizUtils.getCurrentScore(getApplicationContext());
            myRef = db.collection("QuizQuestions").document(ultimateCategory).collection(ultimateCategory + "Questions");
            if (questionsLeft == 0) {
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("Username", mUsername);
                intent.putExtra("CurrentScore", mCurrentScore);
                intent.putExtra("Category", category);
                startActivity(intent);
            } else {
                if (ultimateCategory.equals("Music")) {
                    answerOne.setEnabled(false);
                    answerTwo.setEnabled(false);
                    answerThree.setEnabled(false);
                    answerFour.setEnabled(false);
                    questionTextView.setVisibility(View.INVISIBLE);
                    pictureQuestionTextView.setVisibility(View.VISIBLE);
                    mPlayerView.setVisibility(View.VISIBLE);
                    mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                            getResources(), R.drawable.question_mark
                    ));
                    pictureQuestionTextView.setText("Who wrote this music?");

                    try {
                        final File localFile = File.createTempFile("Music", "mp3");
                        StorageReference myRef = mStorageReference.child("Music/MusicQuestion" + questionsToAsk.get(currentQuestionIndex) + ".mp3");
                        myRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        String toURI = localFile.toURI().toString();
                                        Uri uri = Uri.parse(toURI);
                                        initializeMediaSession();
                                        initializePlayer(uri);
                                        if (isInBackground) {
                                            releasePlayer();

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (ultimateCategory.equals("Pictures")) {
                    questionTextView.setVisibility(View.INVISIBLE);
                    questionImageView.setVisibility(View.VISIBLE);
                    pictureQuestionTextView.setVisibility(View.VISIBLE);

                    try {
                        final File localFile = File.createTempFile("pictures", "jpg");
                        StorageReference myRef = mStorageReference.child("pictures/PictureQuestion" + questionsToAsk.get(currentQuestionIndex) + ".jpg");
                        myRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                       /* String toURI = localFile.toURI().toString();
                                        Uri uri = Uri.parse(toURI);*/
                                        Glide.with(QuestionActivity.this).load(localFile).into(questionImageView);
                                        //Picasso.get().load(localFile).into(questionImageView);
                                        //questionImageView.setImageURI(uri);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                myRef.document(ultimateCategory + "Question" + questionsToAsk.get(currentQuestionIndex)).get()
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



        } else {
            isUltimate = false;


            if (isNewGame) {
                QuizUtils.setCurrentScore(getApplicationContext(), 0);

                currentQuestionIndex = 0;
                questionsLeft = QuestionActivity.totalQuestions;

                questionsToAsk = generateQuestionsToAsk();
                myToolbar.setTitle("Question " + (currentQuestionIndex + 1) + " of " + (questionsLeft + currentQuestionIndex));

            } else {
                currentQuestionIndex = getIntent().getIntExtra(CURRENT_QUESTION_KEY, 1);
                questionsLeft = getIntent().getIntExtra(REMAINING_QUESTIONS_KEY, 0);
                questionsToAsk = getIntent().getIntegerArrayListExtra(QUESTIONS_TO_ASK_KEY);
                myToolbar.setTitle("Question " + (currentQuestionIndex + 1) + " of " + (questionsLeft + currentQuestionIndex));
            }



            myRef = db.collection("QuizQuestions").document(category).collection(category + "Questions");


            mCurrentScore = QuizUtils.getCurrentScore(getApplicationContext());

            if (questionsLeft == 0) {
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("Username", mUsername);
                intent.putExtra("CurrentScore", mCurrentScore);
                intent.putExtra("Category", category);
                startActivity(intent);
            } else {
                if (category.equals("Music")) {
                    answerOne.setEnabled(false);
                    answerTwo.setEnabled(false);
                    answerThree.setEnabled(false);
                    answerFour.setEnabled(false);

                    try {
                        final File localFile = File.createTempFile("Music", "mp3");
                        StorageReference myRef = mStorageReference.child("Music/MusicQuestion" + questionsToAsk.get(currentQuestionIndex) + ".mp3");
                        myRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        String toURI = localFile.toURI().toString();
                                        Uri uri = Uri.parse(toURI);
                                        initializeMediaSession();
                                        initializePlayer(uri);
                                        if (isInBackground) {
                                            releasePlayer();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (category.equals("Pictures")) {
                    try {
                        final File localFile = File.createTempFile("pictures", "jpg");
                        StorageReference myRef = mStorageReference.child("pictures/PictureQuestion" + questionsToAsk.get(currentQuestionIndex) + ".jpg");
                        myRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                       /* String toURI = localFile.toURI().toString();
                                       Uri uri = Uri.parse(toURI);

                                        Log.d(TAG, "onSuccess: " + uri);*/
                                      //questionImageView.setImageURI(uri);
                                        Glide.with(QuestionActivity.this).load(localFile).into(questionImageView);
                                       //Picasso.get().load(uri).placeholder(R.drawable.question_mark).into(questionImageView);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                myRef.document(category + "Question" + questionsToAsk.get(currentQuestionIndex)).get()
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
    }

    private String getUltimateCategory(int questionsLeft) {

        switch (questionsLeft) {
            case 20:
               return "Sport";
            case 19:
                return "Sport";
            case 18:
                return "Music";
            case 17:
                return "Music";
            case 16:
                return "Nature";
            case 15:
                return "Nature";
            case 14:
                return "History";
            case 13:
                return "History";
            case 12:
                return "Geography";
            case 11:
                return "Geography";
            case 10:
                return "Technology";
            case 9:
                return "Technology";
            case 8:
                return "People";
            case 7:
                return "People";
            case 6:
                return "Pictures";
            case 5:
                return "Pictures";
            case 4:
                return "Films";
            case 3:
                return "Films";
            case 2:
                return "TV";
            case 1:
                return "TV";
            default:
                return "Sport";


        }
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(this, TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
        answerOne.setEnabled(true);
        answerTwo.setEnabled(true);
        answerThree.setEnabled(true);
        answerFour.setEnabled(true);
    }
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "myquizapp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    private ArrayList<Integer> generateQuestionsToAsk() {
        ArrayList<Integer> results = new ArrayList<>();

        while (results.size() < totalQuestions) {
            Random rn = new Random();
            int answer = rn.nextInt(QuizQuestionClass.getNumberQuestions(category)) + 1;
            Integer myInt = new Integer(answer);
            if (!results.contains(myInt)) {
                results.add(myInt);
            }
        }
        return results;
    }
    private ArrayList<Integer> generateUltimateQuestionsToAsk() {
        ArrayList<Integer> results = new ArrayList<>();
        int catIndex = 1;
        int questionsIndex = 0;
        while (results.size() < (totalUltimateQuestions)) {
            Random rn = new Random();
            int answer = rn.nextInt(QuizQuestionClass.getNumberQuestions(QuizQuestionClass.getCategories().get(catIndex))) + 1;
            Integer myInt = new Integer(answer);

            if (results.size() == 0 || results.get(results.size() - 1).compareTo(myInt) == 1 || results.get(results.size() - 1).compareTo(myInt) == -1) {
                results.add(myInt);
                questionsIndex++;
                if (questionsIndex >= 2) {
                    catIndex++;
                    questionsIndex = 0;
                }
            }
        }
        Log.d(TAG, "generateUltimateQuestionsToAsk: " + results);
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

