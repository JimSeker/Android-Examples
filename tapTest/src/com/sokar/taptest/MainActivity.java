package com.sokar.taptest;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener {

	View myView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myView = (View) findViewById(R.id.myview);
		myView.setOnTouchListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d("Jim", "touch event");
		if (event.getAction() == MotionEvent.ACTION_UP) {
		  long time=	event.getDownTime();
		  Toast.makeText(getBaseContext(), "Time is"+time, Toast.LENGTH_SHORT).show();
		}
		
		return true;
	}

}
