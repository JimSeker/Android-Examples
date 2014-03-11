package edu.cs4730.notificationdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

/*
 * This is the activity that the notification calls.
 * All this does is check to see that the information is in the intent
 * normally you would have some sort of response to the notification
 * 
 * http://stackoverflow.com/questions/1198558/how-to-send-parameters-from-a-notification-click-to-an-activity
 * http://mobiforge.com/developing/story/displaying-status-bar-notifications-android
 * http://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html
 * http://mobiforge.com/developing/story/displaying-status-bar-notifications-android
 * http://stackoverflow.com/questions/12006724/set-a-combination-of-vibration-lights-or-sound-for-a-notification-in-android
 * http://developer.android.com/reference/android/app/Notification.html
 */

public class receiveActivity extends FragmentActivity {
	TextView Logger;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive);
		String info = "Nothing";
		Logger = (TextView) findViewById(R.id.logger);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			info = extras.getString("mytype");
			if (info == null) {info = "nothing 2"; }
		}
		Logger.setText(info);
	}
}
