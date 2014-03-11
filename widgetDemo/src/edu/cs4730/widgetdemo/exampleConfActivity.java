package edu.cs4730.widgetdemo;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class exampleConfActivity extends Activity implements OnClickListener{
	int randnum = 100;  //default value
	EditText et;
	Button btnok, btncancel;
	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	private static final String PREFS_NAME = "edu.cs4730.widgetdemo.example";
	private static final String PREF_PREFIX_KEY = "appwidget_";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_CANCELED);
		
		setContentView(R.layout.exampleconfactivity);
		et = (EditText)findViewById(R.id.editText1);

		SharedPreferences preferences = getSharedPreferences("example", Context.MODE_PRIVATE);
		//get the key d3 and set a default value of "" if the key doesn't exist.  IE the first time this app is run.
		randnum = preferences.getInt("randnum", 100);
		et.setText(String.valueOf(randnum));
		Log.w("ExampleACtivity"," num is "+ randnum);
		btnok = (Button)findViewById(R.id.ok);
		btnok.setOnClickListener(this);
		btncancel = (Button)findViewById(R.id.cancel);
		btncancel.setOnClickListener(this);
		
	       
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, 
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
		
		
	}
	@Override
	public void onClick(View arg0) {
		final Context context = exampleConfActivity.this;
		
		if (arg0 == btnok) {
		randnum = Integer.parseInt(et.getText().toString());
		saveTitlePref(context, mAppWidgetId, randnum);
		
		///  huh??
		// It is the responsibility of the configuration activity to update
		// the app widget
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		ExampleProvider.updateAppWidget(context, appWidgetManager,	mAppWidgetId);
		
		
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);
		
		}
		finish();  //we are done.
	}
	
	
	// Write the prefix to the SharedPreferences object for this widget
	static void saveTitlePref(Context context, int appWidgetId, int num) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();
		prefs.putInt(PREF_PREFIX_KEY + appWidgetId, num);
		prefs.commit();
	}

	// Read the prefix from the SharedPreferences object for this widget.
	// If there is no preference saved, get the default from a resource
	static int loadTitlePref(Context context, int appWidgetId) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int num = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 0);
		if (num != 0) {
			return num;
		} else {
			return 100;  //default
		}
	}

	static void deleteTitlePref(Context context, int appWidgetId) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
		prefs.remove(PREF_PREFIX_KEY + appWidgetId);
		prefs.commit();
	}
	
}

//int number = (new Random().nextInt(100));