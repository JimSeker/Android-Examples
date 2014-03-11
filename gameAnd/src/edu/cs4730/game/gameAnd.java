package edu.cs4730.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class gameAnd extends Activity {
	protected static final int EXIT_ID = Menu.FIRST;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new mySurfaceView(this));
    }
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, EXIT_ID, 0, "Exit");
		return super.onCreateOptionsMenu(menu);
	}
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case EXIT_ID:
			//System.exit(0);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}