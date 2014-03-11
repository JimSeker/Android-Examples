package edu.cs4730.contentprodemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	String TAG = "MainActivity";
	TextView output;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		output = (TextView) findViewById(R.id.TextView01);
		
        findViewById(R.id.button1).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), contactsDemo.class));
			}
        });
		
		
		logThis("Query for 2");
		//example, select one of them, in this case 2
		Uri onerow = Uri.parse("content://edu.cs4730.provider/square/2");
		Cursor c = getContentResolver().query(onerow, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			do {
				Log.i(TAG, "Value is"+c.getString(0));
				logThis(c.getString(0) + " value is " + c.getString(1));
			} while (c.moveToNext());
		}
		
		logThis("\nQuery all:");
		//now select "all", which will return 1 to 10 squared.
		Uri allrow = Uri.parse("content://edu.cs4730.provider/square");
	    c = getContentResolver().query(allrow, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			do {
				Log.i(TAG, "Value is "+c.getString(0));
				logThis(c.getString(0) + " value is " + c.getString(1));
			} while (c.moveToNext());
		}
		
	}

	public void logThis(String item) {
		output.append("\n" + item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
