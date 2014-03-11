package edu.cs4730.notificationdemo2;


import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	int NotID = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//We are going to call the broadcast receiver from notificationDemo1
				Intent intent = new Intent();
				intent.setAction("edu.cs4730.notificationdemo.broadNotification");
				//adding some extra inform again.
				intent.putExtra("mytype", "From notificationDemo2");
				sendBroadcast(intent);
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//We are going to call the broadcast receiver from notificationDemo1
				Intent intent = new Intent();
				intent.setAction("edu.cs4730.notificationdemo.broadNotification");
				//adding some extra inform again.
				intent.putExtra("mytype", "alarm from notificationDemo2");

		        //---use the AlarmManager to trigger an alarm---
		        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);                 

		        //---get current date and time---
		        Calendar calendar = Calendar.getInstance();

		        //---sets the time for the alarm to trigger in 2 minutes from now---
		        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) +2);
		        calendar.set(Calendar.SECOND, 0);


				PendingIntent contentIntent = PendingIntent.getBroadcast(MainActivity.this, NotID, intent, 0);
		        Log.i("MainACtivity", "Set alarm, I hope");
		             

		        //---sets the alarm to trigger---
		        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), contentIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
