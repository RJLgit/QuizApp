package com.example.android.myquizapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link QuizAppWidgetConfigureActivity QuizAppWidgetConfigureActivity}
 */
public class QuizAppWidget extends AppWidgetProvider {
    FirebaseFirestore db;
    private DocumentReference myRef;
    private FirebaseAuth mFirebaseAuth;
    private String uniqueUserId;
    int score;
    private static final String TAG = "QuizAppWidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int s) {

        CharSequence widgetText = QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.score_text_widget, "Your high score is " + s);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate: ");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uniqueUserId = user.getUid();
        db = FirebaseFirestore.getInstance();
        myRef = db.collection("TopScores").document(uniqueUserId);
        for (final int appWidgetId : appWidgetIds) {
            myRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                TopScores myScores = documentSnapshot.toObject(TopScores.class);
                                score = myScores.getScoreByCategory(QuizAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId).toString());
                                updateAppWidget(context, appWidgetManager, appWidgetId, score);
                            }
                        }
                    });

        }
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
}

