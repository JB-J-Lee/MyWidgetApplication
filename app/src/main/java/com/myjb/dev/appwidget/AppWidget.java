package com.myjb.dev.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.myjb.dev.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private static final String TAG = "AppWidget";

    public static final String EXTRA = "EXTRA";
    public static final String APPWIDGET = "APPWIDGET";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String str) {

        Log.e(TAG, "[updateAppWidget] appWidgetId : " + appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.p_appwidget);

        Intent intent = new Intent(context, AppWidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        if (!TextUtils.isEmpty(str)) {
            views.setTextViewText(R.id.appwidget_text, "[" + appWidgetId + "]" + (TextUtils.isEmpty(str) ? "" : " " + str));
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences prefs = context.getSharedPreferences(APPWIDGET, MODE_PRIVATE);
        String str = prefs.getString(EXTRA, EXTRA);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, str);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
        Log.e(TAG, "[onEnabled] ");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
        Log.e(TAG, "[onDisabled] ");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.e(TAG, "[onDisabled] ");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.e(TAG, "[onRestored] ");
    }

    /**
     * Comment by JB.
     * <p>
     * Update data using SharedPreferences rather than Intent.
     * The parent class AppWidgetProvider's onReceive does not handle the intent and inherits it.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "[onReceive] ");

        String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {

            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }

            int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            if (appWidgetIds != null && appWidgetIds.length > 0) {

                String str = extras.getString(EXTRA, EXTRA);
                Log.e(TAG, "[onReceive] EXTRA : " + str);

                if (TextUtils.isEmpty(str)) {
                    onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
                } else {
                    for (int appWidgetId : appWidgetIds) {
                        updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId, str);
                    }
                }
            }
        } else {
            super.onReceive(context, intent);
        }
    }
}