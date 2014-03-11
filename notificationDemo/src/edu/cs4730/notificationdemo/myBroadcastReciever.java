package edu.cs4730.notificationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


/*
 * A broadcast receiver doesn't have a screen.
 * We could easy use this instead of DisplayNotification activity, because we don't
 * need a screen.
 */
public class myBroadcastReciever extends BroadcastReceiver {
	//we can have multiple Actions for this broadcastReceiver.
	//a note, I have only declared one action though, but we could add more later.
	private static final String ACTION = "edu.cs4730.notificationdemo.broadNotification";

	@Override
	public void onReceive(Context context, Intent intent) {
		String info= "no bundle";


		Toast.makeText(context, "received something", Toast.LENGTH_SHORT).show();
		Log.i("myBroadcastReceiver", "received something");
		if (intent.getAction().equals(ACTION)){  //is it our action?
			Bundle extras = intent.getExtras();
			if (extras != null) {
				info = extras.getString("mytype");
				if (info == null) {
					info = "nothing"; 
			    }
			}
			Toast.makeText(context, "intent has: " + info, Toast.LENGTH_SHORT).show();
			Log.i("myBroadcastReceiver", "intent has: " + info);
		}
	}

}
