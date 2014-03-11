package edu.cs4730.notificationdemo;

/*
 * This activity will not display to the screen (hopefully).  It just sets a notification
 * and then exits (finish())
 * 
 * http://mobiforge.com/developing/story/displaying-status-bar-notifications-android
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class DisplayNotification extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //---get the notification ID for the notification; 
        // passed in by the MainActivity---
        int notifID = getIntent().getExtras().getInt("NotifID");
 
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //---PendingIntent to launch activity if the user selects 
        // the notification---
    	Intent notificationIntent = new Intent(getApplicationContext(), receiveActivity.class);
		notificationIntent.putExtra("mytype", "2 minutes later?");
        PendingIntent contentIntent = PendingIntent.getActivity(this, notifID, notificationIntent, 0);
        
        //create the notification
		Notification notif = new NotificationCompat.Builder(getApplicationContext())
		.setSmallIcon(R.drawable.ic_launcher)
		.setWhen(System.currentTimeMillis())  //When the event occurred, now, since noti are stored by time.
		.setContentTitle("Time's up!")   //Title message top row.
		.setContentText("This is your alert, courtesy of the AlarmManager")  //message when looking at the notification, second row
		.setContentIntent(contentIntent)  //what activity to open.
		.setAutoCancel(true)   //allow auto cancel when pressed.
		.build();  //finally build and return a Notification.

		//Show the notification
        nm.notify(notifID, notif);
        //---destroy the activity---
        finish();
    }
}
