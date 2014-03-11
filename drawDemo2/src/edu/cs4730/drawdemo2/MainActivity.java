package edu.cs4730.drawdemo2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

/*
 * This demos a custom view.  There is almost nothing in this activity
 * except for a call to the customview to reset the grid from a menu item.
 * 
 */


public class MainActivity extends Activity {

	DrawView dv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dv = (DrawView)findViewById(R.id.dv1);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reset:
			dv.clearmaze();
			return true;
		}
		return false;
	}
}
