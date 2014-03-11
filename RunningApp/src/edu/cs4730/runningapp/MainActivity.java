package edu.cs4730.runningapp;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button call;
	TextView output;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        findViewById(R.id.btn).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getlist();
			}
        });
		output = (TextView) findViewById(R.id.ouput);
		
	}

	public void getlist() {
		
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		// getRunningServices,  getRunningAppProcesses, getRunningTasks, and getRecentTasks 
	    List<RunningServiceInfo> runServices = activityManager.getRunningServices(Integer.MAX_VALUE);
		List<RunningAppProcessInfo> runApp = activityManager.getRunningAppProcesses();
		List<RunningTaskInfo> runTasks = activityManager.getRunningTasks(Integer.MAX_VALUE); 
		List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(Integer.MAX_VALUE, ActivityManager.RECENT_WITH_EXCLUDED);
		
		output.setText("Running Tasks list:");
		for (RunningTaskInfo task : runTasks) {
			output.append("\n"+ task.baseActivity.getPackageName()+"("+ task.baseActivity.getShortClassName()+")");
		}
		
		output.append("\n\nRunning Services list:");
		for (RunningServiceInfo task : runServices) {
			output.append("\n"+ task.service.getPackageName());
		}
		
		output.append("\n\nAppProcess list:");
		for (RunningAppProcessInfo task : runApp) {
			//output.append("\n"+ task.importanceReasonComponent.getPackageName());
			output.append("\n"+ task.processName);
		}
		
		output.append("\n\nRecent Task list:");
		for (RecentTaskInfo task : recentTasks) {
			//output.append("\n"+ task.origActivity.getPackageName());
			output.append("\n"+ task.baseIntent.getComponent().getPackageName());
		}
		
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
