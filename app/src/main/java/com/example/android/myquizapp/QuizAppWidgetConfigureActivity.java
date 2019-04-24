package com.example.android.myquizapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The configuration screen for the {@link QuizAppWidget QuizAppWidget} AppWidget.
 */
public class QuizAppWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.example.android.myquizapp.QuizAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_MODE_KEY = "appwidget_simple_detailed";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    //int mAppWidgetId;
    EditText mAppWidgetText;
    Spinner mSpinner;
    FirebaseFirestore db;
    private DocumentReference myRef;
    private FirebaseAuth mFirebaseAuth;
    private String uniqueUserId;
    Button mButton;
    Context context;
    String s;
    boolean topScoresExists;
    private static final String TAG = "QuizAppWidgetConfigureA";
    private String mUsername;
    int highScore;
    /*View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = QuizAppWidgetConfigureActivity.this;
            final String s = mSpinner.getSelectedItem().toString();
            final boolean topScoresExists;
            if (QuizAppWidget.topScores.size() == 0) {
                topScoresExists = false;
            } else {
                topScoresExists = true;
            }

            mFirebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            uniqueUserId = user.getUid();
            db = FirebaseFirestore.getInstance();
            mUsername = user.getDisplayName();

            myRef = db.collection("TopScores").document(uniqueUserId);
            myRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                TopScores myScores = documentSnapshot.toObject(TopScores.class);
                                for (int i = 0; i < QuizQuestionClass.getCategories().size(); i++) {
                                    int score = myScores.getScoreByCategory(QuizQuestionClass.getCategories().get(i));
                                    if (topScoresExists) {
                                        QuizAppWidget.topScores.set(i, "" + score);
                                    } else {
                                        QuizAppWidget.topScores.add("" + score);
                                    }

                                }
                                saveModePref(context, mAppWidgetId, s);
                                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);



                                    //updateAppWidget(context, appWidgetManager, appWidgetId, widgetMode.toString());
                                    RemoteViews views;
                                    if (s.equals("Simple")) {
                                        views = new RemoteViews(context.getPackageName(), R.layout.simple_quiz_app_widget);
                                        views.setTextViewText(R.id.simple_widget_textview, "Quiz App");
                                        Intent loadIntent = new Intent(context, MainActivity.class);
                                        PendingIntent loadPendingIntent = PendingIntent.getActivity(context, 1, loadIntent, 0);
                                        views.setOnClickPendingIntent(R.id.simple_widget_textview, loadPendingIntent);
                                    } else {

                                        Intent serviceIntent = new Intent(context, QuizAppWidgetService.class);
                                        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

                                        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

                                        Intent refreshIntent = new Intent(context, QuizAppWidget.class);
                                        refreshIntent.setAction(QuizAppWidget.ACTION_OPEN_ACTIVITY);

                                        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, 0);

                                        Intent loadIntentTwo = new Intent(context, QuizAppWidget.class);

                                        loadIntentTwo.setAction(QuizAppWidget.ACTION_UPDATE_WIDGET);
                                        loadIntentTwo.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

                                        PendingIntent loadPendingIntentTwo = PendingIntent.getBroadcast(context, 3, loadIntentTwo, 0);

            *//*CharSequence widgetText = QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
            // Construct the RemoteViews object*//*
                                        views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget);
                                        views.setOnClickPendingIntent(R.id.refresh_widget, loadPendingIntentTwo);
                                        views.setPendingIntentTemplate(R.id.widget_stack_view, refreshPendingIntent);
                                        //views.setOnClickPendingIntent(R.id.refresh_widget, refreshPendingIntent);
           *//* views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setTextViewText(R.id.score_text_widget, "Your high score is " + s);*//*
                                        views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
                                        views.setEmptyView(R.id.widget_stack_view, R.id.empty_widget_view);
                                        //views.setPendingIntentTemplate(R.id.widget_stack_view, refreshPendingIntent);
                                        appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.widget_stack_view);

                                    }

                                    if (views != null) {
                                        appWidgetManager.updateAppWidget(mAppWidgetId, views);

                                        Intent resultValue = new Intent();
                                        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                        setResult(RESULT_OK, resultValue);
                                        finish();
                                    }

                            }
                        }
                    });*/



           /* final Context context = QuizAppWidgetConfigureActivity.this;
            final String spinText = mSpinner.getSelectedItem().toString();
            // When the button is clicked, store the string locally
            //String widgetText = mAppWidgetText.getText().toString();
            mFirebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            uniqueUserId = user.getUid();
            db = FirebaseFirestore.getInstance();
            myRef = db.collection("TopScores").document(uniqueUserId);
            myRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                TopScores myScores = documentSnapshot.toObject(TopScores.class);
                                for (int i = 0; i < QuizQuestionClass.getCategories().size(); i++) {
                                    int score = myScores.getScoreByCategory(QuizQuestionClass.getCategories().get(i));
                                    if (QuizAppWidget.topScores != null) {
                                        QuizAppWidget.topScores.set(i, "" + score);
                                    } else {
                                        QuizAppWidget.topScores.add("" + score);
                                    }

                                }

                            }
                            saveModePref(context, mAppWidgetId, spinText);
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                            QuizAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId, spinText);
                            Intent resultValue = new Intent();
                            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                            setResult(RESULT_OK, resultValue);
                            finish();
                        }
                    });

                        }
    };*/


            /*mFirebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            uniqueUserId = user.getUid();
            db = FirebaseFirestore.getInstance();
            myRef = db.collection("TopScores").document(uniqueUserId);

                myRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    TopScores myScores = documentSnapshot.toObject(TopScores.class);
                                    highScore = myScores.getScoreByCategory(spinText);
                                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                                    //maybe load from db here
                                    QuizAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId, highScore);

                                    // Make sure we pass back the original appWidgetId
                                    Intent resultValue = new Intent();
                                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                    setResult(RESULT_OK, resultValue);
                                    finish();
                                }
                            }
                        });*/
            // It is the responsibility of the configuration activity to update the app widget



    static void saveModePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_MODE_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadModePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_MODE_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteModePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_MODE_KEY + appWidgetId);
        prefs.apply();
    }

    public QuizAppWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        /*// Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);*/

        setContentView(R.layout.quiz_app_widget_configure);
        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        mSpinner = findViewById(R.id.spinner1);
        mButton = findViewById(R.id.add_button);

        //findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            Log.d(TAG, "onCreate: " + mAppWidgetId);
            Log.d(TAG, "onCreate: " + AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();

        }

       /* mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = QuizAppWidgetConfigureActivity.this;
                s = mSpinner.getSelectedItem().toString();
                if (QuizAppWidget.topScores.size() == 0) {
                    topScoresExists = false;
                } else {
                    topScoresExists = true;
                }

                mFirebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                uniqueUserId = user.getUid();
                db = FirebaseFirestore.getInstance();
                mUsername = user.getDisplayName();

                myRef = db.collection("TopScores").document(uniqueUserId);
                myRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    TopScores myScores = documentSnapshot.toObject(TopScores.class);
                                    for (int i = 0; i < QuizQuestionClass.getCategories().size(); i++) {
                                        int score = myScores.getScoreByCategory(QuizQuestionClass.getCategories().get(i));
                                        if (topScoresExists) {
                                            QuizAppWidget.topScores.set(i, "" + score);
                                        } else {
                                            QuizAppWidget.topScores.add("" + score);
                                        }

                                    }
                                    saveModePref(context, mAppWidgetId, s);
                                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);



                                    //updateAppWidget(context, appWidgetManager, appWidgetId, widgetMode.toString());
                                    RemoteViews views;
                                    if (s.equals("Simple")) {
                                        views = new RemoteViews(context.getPackageName(), R.layout.simple_quiz_app_widget);
                                        views.setTextViewText(R.id.simple_widget_textview, "Quiz App");
                                        Intent loadIntent = new Intent(context, MainActivity.class);
                                        PendingIntent loadPendingIntent = PendingIntent.getActivity(context, 1, loadIntent, 0);
                                        views.setOnClickPendingIntent(R.id.simple_widget_textview, loadPendingIntent);
                                        appWidgetManager.updateAppWidget(mAppWidgetId, views);
                                        Intent resultValue = new Intent();
                                        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                        setResult(RESULT_OK, resultValue);
                                        finish();
                                    } else {

                                        Intent serviceIntent = new Intent(context, QuizAppWidgetService.class);
                                        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                        Log.d(TAG, "onSuccess: " + mAppWidgetId);
                                        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

                                        Intent refreshIntent = new Intent(context, QuizAppWidget.class);
                                        refreshIntent.setAction(QuizAppWidget.ACTION_OPEN_ACTIVITY);

                                        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, 0);

                                        Intent loadIntentTwo = new Intent(context, QuizAppWidget.class);

                                        loadIntentTwo.setAction(QuizAppWidget.ACTION_UPDATE_WIDGET);
                                        loadIntentTwo.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                        Log.d(TAG, "onSuccess: " + mAppWidgetId);

                                        PendingIntent loadPendingIntentTwo = PendingIntent.getBroadcast(context, 3, loadIntentTwo, 0);

            *//*CharSequence widgetText = QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
            // Construct the RemoteViews object*//*
                                        views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget);
                                        views.setOnClickPendingIntent(R.id.refresh_widget, loadPendingIntentTwo);
                                        views.setPendingIntentTemplate(R.id.widget_stack_view, refreshPendingIntent);
                                        //views.setOnClickPendingIntent(R.id.refresh_widget, refreshPendingIntent);
           *//* views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setTextViewText(R.id.score_text_widget, "Your high score is " + s);*//*
                                        views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
                                        views.setEmptyView(R.id.widget_stack_view, R.id.empty_widget_view);
                                        //views.setPendingIntentTemplate(R.id.widget_stack_view, refreshPendingIntent);
                                        appWidgetManager.updateAppWidget(mAppWidgetId, views);
                                        appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.widget_stack_view);
                                        Intent resultValue = new Intent();
                                        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                        setResult(RESULT_OK, resultValue);
                                        finish();

                                    }

                                    *//*if (views != null) {



                                    }*//*

                                }
                            }
                        });
            }
        });*/

       // mAppWidgetText.setText(loadTitlePref(QuizAppWidgetConfigureActivity.this, mAppWidgetId));
    }
    public void configSetUp(View v) {
        context = QuizAppWidgetConfigureActivity.this;
        s = mSpinner.getSelectedItem().toString();
        if (QuizAppWidget.topScores.size() == 0) {
            topScoresExists = false;
        } else {
            topScoresExists = true;
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uniqueUserId = user.getUid();
        db = FirebaseFirestore.getInstance();
        mUsername = user.getDisplayName();

        myRef = db.collection("TopScores").document(uniqueUserId);
        myRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            TopScores myScores = documentSnapshot.toObject(TopScores.class);
                            for (int i = 0; i < QuizQuestionClass.getCategories().size(); i++) {
                                int score = myScores.getScoreByCategory(QuizQuestionClass.getCategories().get(i));
                                if (topScoresExists) {
                                    QuizAppWidget.topScores.set(i, "" + score);
                                } else {
                                    QuizAppWidget.topScores.add("" + score);
                                }

                            }
                            saveModePref(context, mAppWidgetId, s);
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);



                            //updateAppWidget(context, appWidgetManager, appWidgetId, widgetMode.toString());

                            RemoteViews views;
                            if (s.equals("Simple")) {
                                views = new RemoteViews(context.getPackageName(), R.layout.simple_quiz_app_widget);
                                views.setTextViewText(R.id.simple_widget_textview, "Quiz App");
                                Intent loadIntent = new Intent(context, MainActivity.class);
                                PendingIntent loadPendingIntent = PendingIntent.getActivity(context, 1, loadIntent, 0);
                                views.setOnClickPendingIntent(R.id.simple_widget_textview, loadPendingIntent);
                                appWidgetManager.updateAppWidget(mAppWidgetId, views);
                                Intent resultValue = new Intent();
                                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                setResult(RESULT_OK, resultValue);
                                finish();
                            } else {

                                Intent serviceIntent = new Intent(context, QuizAppWidgetService.class);
                                serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                Log.d(TAG, "onSuccess: " + mAppWidgetId);
                                serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

                                Intent loadIntent = new Intent(context, MainActivity.class);
                                PendingIntent loadPendingIntent = PendingIntent.getActivity(context, 1, loadIntent, 0);



                                Intent openQuestionActivityIntent = new Intent(context, QuizAppWidget.class);
                                openQuestionActivityIntent.setAction(QuizAppWidget.ACTION_OPEN_ACTIVITY);

                                PendingIntent openQuestionActivityPendingIntent = PendingIntent.getBroadcast(context, 0, openQuestionActivityIntent, 0);

                                Intent refreshWidgetIntent = new Intent(context, QuizAppWidget.class);

                                refreshWidgetIntent.setAction(QuizAppWidget.ACTION_UPDATE_WIDGET);
                                refreshWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                Log.d(TAG, "onSuccess: " + mAppWidgetId);

                                PendingIntent refreshWidgetPendingIntent = PendingIntent.getBroadcast(context, mAppWidgetId, refreshWidgetIntent, 0);

            /*CharSequence widgetText = QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
            // Construct the RemoteViews object*/
                                views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget);
                                views.setOnClickPendingIntent(R.id.refresh_widget, refreshWidgetPendingIntent);
                                views.setPendingIntentTemplate(R.id.widget_stack_view, openQuestionActivityPendingIntent);
                                views.setOnClickPendingIntent(R.id.appwidget_text, loadPendingIntent);
                                //views.setOnClickPendingIntent(R.id.refresh_widget, openQuestionActivityPendingIntent);
           /* views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setTextViewText(R.id.score_text_widget, "Your high score is " + s);*/
                                views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
                                views.setEmptyView(R.id.widget_stack_view, R.id.empty_widget_view);
                                //views.setPendingIntentTemplate(R.id.widget_stack_view, openQuestionActivityPendingIntent);
                                appWidgetManager.updateAppWidget(mAppWidgetId, views);
                                appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.widget_stack_view);
                                Intent resultValue = new Intent();
                                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                                setResult(RESULT_OK, resultValue);
                                finish();

                            }

                                    /*if (views != null) {



                                    }*/

                        }
                    }
                });
    }
}

