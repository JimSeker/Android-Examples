package edu.cs4730.widgetdemo;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

//http://www.vogella.com/articles/AndroidWidgets/article.html
//http://code4reference.com/2012/07/android-widget-tutorial/
//http://developer.android.com/guide/topics/appwidgets/index.html
//http://developer.android.com/guide/practices/ui_guidelines/widget_design.html


public class ExampleProvider extends AppWidgetProvider {

	//private static final String ACTION_CLICK = "ACTION_CLICK";
	//int randnum = 100;  //default value

/*
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		SharedPreferences preferences = context.getSharedPreferences("example", Context.MODE_PRIVATE);
		//get the key d3 and set a default value of "" if the key doesn't exist.  IE the first time this app is run.
		randnum = preferences.getInt("randnum", 100);
		// Log.w("widget", "randnum is "+ randnum);

		// Get all ids
		ComponentName thisWidget = new ComponentName(context, Example.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			//for (int widgetId : appWidgetIds) {
			// Create some random data
			int number = (new Random().nextInt(randnum));

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example);
			Log.w("WidgetExample", String.valueOf(number));
			// Set the text
			remoteViews.setTextViewText(R.id.update, String.valueOf(number));

			// Register an onClickListener
			Intent intent = new Intent(context, Example.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
	
	
*/
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	
		// There may be multiple widgets active, so update all of them
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
		}
	}
	
	
	
	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

		// Create some random number from the shared preference number stored in the configure activity.
		int number = (new Random().nextInt( 
				exampleConfActivity.loadTitlePref(context, appWidgetId)  //get the number via the shared preferences.
				));
		
		// Construct the RemoteViews object
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.examplewidget);
		Log.w("WidgetExample", String.valueOf(number));
		
		// Set the text
		remoteViews.setTextViewText(R.id.update, String.valueOf(number));
		
		// Register an onClickListener
		Intent intent = new Intent(context, ExampleProvider.class);

		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		
		//if I wanted every widget to up use this section that is commented out
		//ComponentName thisWidget = new ComponentName(context, Example.class);
		//int[] ids = appWidgetManager.getAppWidgetIds(thisWidget);
		
		//if I want only this one to update, then use this code.
		int[] ids = {appWidgetId};
		
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		//intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// When the user deletes the widget, delete the preference associated
		// with it.
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			exampleConfActivity.deleteTitlePref(context,
					appWidgetIds[i]);
		}
	}

	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}
	
}
