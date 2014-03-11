package edu.cs4730.input2;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector.OnGestureListener;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;


/*
 * example from http://developer.android.com/training/gestures/detector.html
 * 
 * sets a gesture detector on the "screen".     There is no real layout.
 *  needs testing with say a edittext field and button to see what if anything breaks.
 *  
 *  we are overriding the default ontouchlistener (onTouchEvent) to call the gesture detector.
 *  
 *  Also overriding the default onKeyDown()  for the activity, this maybe not be good idea
 *  if there are widgets that also need the key events such as a EditText.
 */

public class MainActivity extends Activity implements  OnGestureListener, OnDoubleTapListener {

	private static final String DEBUG_TAG = "Gestures";
	private GestureDetectorCompat mDetector; 
	char[] chars = {'a','b','c','d','e', 'f', 'g', 'h', 'i', 'j', 'k', 'l','m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		mDetector = new GestureDetectorCompat(this,this);
		// Set the gesture detector as the double tap
		// listener.
		mDetector.setOnDoubleTapListener(this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		char key = event.getMatch(chars);
		if (key != '\0') {
			Log.d(DEBUG_TAG,"onKeyDown: " + key); 
			Toast.makeText(getApplicationContext(), "onKeyDown: " + key, Toast.LENGTH_SHORT).show();
			return true;
		} 

		return false;
	}

	@Override 
	public boolean onTouchEvent(MotionEvent event){ 
		this.mDetector.onTouchEvent(event);
		// Be sure to call the superclass implementation
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent event) { 
		Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
		Toast.makeText(getApplicationContext(), "onDown: " + event.toString(), Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, 
			float velocityX, float velocityY) {
		Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
		Toast.makeText(getApplicationContext(), "onFling: " + event1.toString()+event2.toString(), Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "onLongPress: " + event.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onLongPress: " + event.toString()); 
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Toast.makeText(getApplicationContext(), "onScroll: " + e1.toString()+e2.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
		return true;
	}

	@Override
	public void onShowPress(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "onShowPress: " + event.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "onSingleTapUp: " + event.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "onDoubleTap: " + event.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "onDoubleTapEvent: " + event.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "onSingleTapConfirmed: " + event.toString(), Toast.LENGTH_SHORT).show();
		Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
		return true;
	}



}
