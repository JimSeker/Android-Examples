package edu.cs4730.widgetdemo2;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class Widgetd2Provider extends AppWidgetProvider {
	//private static final String ACTION_CLICK = "ACTION_CLICK";
	int randnum = 100;  //default value

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		SharedPreferences preferences = context.getSharedPreferences("example", Context.MODE_PRIVATE);
		//get the key d3 and set a default value of "" if the key doesn't exist.  IE the first time this app is run.
		randnum = preferences.getInt("randnum", 100);
		// Log.w("widget", "randnum is "+ randnum);

		// Get all ids
		ComponentName thisWidget = new ComponentName(context, Widgetd2Provider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			//for (int widgetId : appWidgetIds) {
			// Create some random data
			int number = (new Random().nextInt(randnum));

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			Log.w("WidgetExample", String.valueOf(number));
			// Set the text
			remoteViews.setTextViewText(R.id.update, String.valueOf(number));

			// Register an onClickListener
			Intent intent = new Intent(context,Widgetd2Provider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			//if I wanted every widget to up use this section that is commented out
			//ComponentName thisWidget = new ComponentName(context, Example.class);
			//int[] ids = appWidgetManager.getAppWidgetIds(thisWidget);
			
			//if I want only this one to update, then use this code.
			int[] ids = {widgetId};
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
			//if I wanted every widget to update, then use this code, (commented out)
			//intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}

}
