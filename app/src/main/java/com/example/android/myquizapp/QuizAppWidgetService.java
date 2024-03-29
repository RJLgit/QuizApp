package com.example.android.myquizapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class QuizAppWidgetService extends RemoteViewsService {



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new QuizWidgetItemFactory(this.getApplicationContext(), intent);
    }

    class QuizWidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private ArrayList<String> data = QuizQuestionClass.getCategories();
        private ArrayList<String> topScores = new ArrayList<>();
        FirebaseFirestore db;
        private DocumentReference myRef;
        private FirebaseAuth mFirebaseAuth;
        private String uniqueUserId;
        private static final String TAG = "QuizWidgetItemFactory";
        boolean topScoresExists;


        public QuizWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            mFirebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            if (user != null) {
                uniqueUserId = user.getUid();
                db = FirebaseFirestore.getInstance();
                myRef = db.collection("TopScores").document(uniqueUserId);
                for (int i = 0; i < QuizAppWidget.topScores.size(); i++) {
                    topScores.add(QuizAppWidget.topScores.get(i));
                }

            }


        }

        @Override
        public void onDataSetChanged() {
            /*topScores.clear();
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");
            topScores.add("100");*/
            Log.d(TAG, "onDataSetChanged: " + topScores);

    topScores.clear();

                                for (int i = 0; i < QuizAppWidget.topScores.size(); i++) {
                                    topScores.add(QuizAppWidget.topScores.get(i));
                                    Log.d(TAG, "onDataSetChanged: " + topScores);
                                }


        }




        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            //SystemClock.sleep(500);
            Log.d(TAG, "getViewAt: " + QuizQuestionClass.getCategories().get(i));
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quiz_app_widget_item);
            if (QuizAppWidget.topScores.size() != 0) {
                views.setTextViewText(R.id.widget_textview_item, data.get(i) + "\nTop score: " + topScores.get(i));
            } else {
                views.setTextViewText(R.id.widget_textview_item, data.get(i));
            }
            Intent fillIntent = new Intent();
            fillIntent.putExtra(QuizAppWidget.CATEGORY_CLICKED, data.get(i));
            views.setOnClickFillInIntent(R.id.widget_textview_item, fillIntent);
            /*Intent fillIntent = new Intent();
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setOnClickFillInIntent(R.id.widget_textview_item, fillIntent);*/
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {

            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
