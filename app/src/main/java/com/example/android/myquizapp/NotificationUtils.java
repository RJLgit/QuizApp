package com.example.android.myquizapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class NotificationUtils {
    private static final int NOT_INTERVAL_MINUTES = 1;
    private static final int NOT_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(NOT_INTERVAL_MINUTES));
    private static final int FLEXTIME_SECONDS = NOT_INTERVAL_SECONDS;
    private static final String NOTIFICATION_JOB_TAG = "quiz_app_job_tag";
    private static boolean jobStarted;

    private static final int PENDING_INTENT_ID = 96543;
    public static final int UNIQUE_QUIZ_APP_NOTIFICATION_ID = 96543423;
    public static final String ACTION_DISMISS_NOTIFICATION = "quizappdismiss";
    private static final String NOTIFICATION_CHANNEL_ID_QUIZ_APP = "quiz_app_notification_channel";
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, PENDING_INTENT_ID, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private static Bitmap getNotIcon(Context context) {
        Resources res = context.getResources();
        Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.tiny_notifcation_image);
        return icon;
    }

    synchronized public static void scheduleNotificationUpdate(@NonNull final Context context) {
        if (jobStarted) {
            return;
        }
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag(NOTIFICATION_JOB_TAG)
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        NOT_INTERVAL_SECONDS, NOT_INTERVAL_SECONDS + FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintJob);
        jobStarted = true;
    }

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


    public static void updateUserAboutTopScores(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID_QUIZ_APP, context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_QUIZ_APP)
                .setSmallIcon(R.drawable.tiny_notifcation_image)
                .setLargeIcon(getNotIcon(context))
                .setContentTitle("Ultimate Quiz App")
                .setContentText("Try to beat the quiz scores")
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(UNIQUE_QUIZ_APP_NOTIFICATION_ID, notificationBuilder.build());
    }

}
