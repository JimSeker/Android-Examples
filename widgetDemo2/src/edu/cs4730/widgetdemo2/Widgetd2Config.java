package edu.cs4730.widgetdemo2;

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

public class Widgetd2Config extends Activity implements OnClickListener{
	int randnum = 100;  //default value
	EditText et;
	Button btnok, btncancel;
	int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setResult(RESULT_CANCELED);
		
		setContentView(R.layout.widget_config);
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
		if (arg0 == btnok) {
		SharedPreferences preferences = getSharedPreferences("example", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		randnum = Integer.parseInt(et.getText().toString());
		editor.putInt("randnum",randnum);
		editor.commit();
		
		
		
		
		//if I had an update for method for it put the call here.  BUt i'm using SharedPreferences.
		//final Context context = exampleActivity.this;
		//AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		//Example.updateAppWidget(context, appWidgetManager, mAppWidgetId);
		
//		Toast.makeText(context, "HelloWidgetConfig.onClick(): " + String.valueOf(mAppWidgetId) , Toast.LENGTH_LONG).show();
		
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);
		
		}
		finish();  //we are done.
	}
}
