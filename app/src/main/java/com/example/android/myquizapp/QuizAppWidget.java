package com.example.android.myquizapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link QuizAppWidgetConfigureActivity QuizAppWidgetConfigureActivity}
 */
public class QuizAppWidget extends AppWidgetProvider {
    public static final String ACTION_UPDATE_WIDGET = "QuizAppWidget_Update_Action";
    public static final String ACTION_OPEN_ACTIVITY = "QuizAppWidget_Open_Action";
    FirebaseFirestore db;
    private DocumentReference myRef;
    private FirebaseAuth mFirebaseAuth;
    private String uniqueUserId;
    int score;
    public static ArrayList<String> topScores = new ArrayList<>();
    private static final String TAG = "QuizAppWidget";




    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String s) {
        RemoteViews views;
        if (s.equals("Simple")) {
            views = new RemoteViews(context.getPackageName(), R.layout.simple_quiz_app_widget);
            views.setTextViewText(R.id.simple_widget_textview, "Quiz App");
            Intent loadIntent = new Intent(context, MainActivity.class);
            PendingIntent loadPendingIntent = PendingIntent.getActivity(context, 0, loadIntent, 0);
            views.setOnClickPendingIntent(R.id.simple_widget_textview, loadPendingIntent);
        } else {

            Intent serviceIntent = new Intent(context, QuizAppWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            serviceIntent.setData(Uri. parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            Intent refreshIntent = new Intent(context, QuizAppWidget.class);
            refreshIntent.setAction(ACTION_OPEN_ACTIVITY);

            PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, 0);

            Intent loadIntentTwo = new Intent(context, MainActivity.class);
            PendingIntent loadPendingIntentTwo = PendingIntent.getActivity(context, 0, loadIntentTwo, 0);

            /*CharSequence widgetText = QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
            // Construct the RemoteViews object*/
            views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget);
           views.setOnClickPendingIntent(R.id.refresh_widget, loadPendingIntentTwo);
           views.setPendingIntentTemplate(R.id.widget_stack_view, refreshPendingIntent);
            //views.setOnClickPendingIntent(R.id.refresh_widget, refreshPendingIntent);
           /* views.setTextViewText(R.id.appwidget_text, widgetText);
            views.setTextViewText(R.id.score_text_widget, "Your high score is " + s);*/
            views.setRemoteAdapter(R.id.widget_stack_view, serviceIntent);
            views.setEmptyView(R.id.widget_stack_view, R.id.empty_widget_view);
            views.setPendingIntentTemplate(R.id.widget_stack_view, refreshPendingIntent);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_stack_view);
        }


        // Instruct the widget manager to update the widget
        if (views != null) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static void newTopScore(String category, int newScore) {
        if (topScores.size() != 0) {
            int index = topScores.indexOf(category);
            if (index >= 0) {
                topScores.set(index, newScore + "");
            }
        }

    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

            final boolean topScoresExists;
            if (topScores.size() == 0) {
                topScoresExists = false;
            } else {
                topScoresExists = true;
            }
            Log.d(TAG, "onUpdate: ");
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
                                if (topScoresExists) {
                                    topScores.set(i, "" + score);
                                } else {
                                    topScores.add("" + score);
                                }

                            }
                            for (final int appWidgetId : appWidgetIds) {
                                CharSequence widgetMode = QuizAppWidgetConfigureActivity.loadModePref(context, appWidgetId);

                                updateAppWidget(context, appWidgetManager, appWidgetId, widgetMode.toString());


                            }
                        }
                    }
                });


    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            QuizAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        //CharSequence widgetText = QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget);

        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_UPDATE_WIDGET.equals(intent.getAction())) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_stack_view);
        }
        if (ACTION_OPEN_ACTIVITY.equals(intent.getAction())) {
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

        super.onReceive(context, intent);
    }
}

