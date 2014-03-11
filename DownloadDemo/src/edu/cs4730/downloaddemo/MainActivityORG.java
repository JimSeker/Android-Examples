package edu.cs4730.downloaddemo;

import java.io.FileNotFoundException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
 * http://developer.android.com/reference/android/app/DownloadManager.Request.html#setNotificationVisibility%28int%29
 * http://developer.android.com/reference/android/app/DownloadManager.html
 * http://sunil-android.blogspot.com/2013/01/pass-data-from-service-to-activity.html
 * http://stackoverflow.com/questions/7239996/android-downloadmanager-api-opening-file-after-download
 * 
 */


public class MainActivityORG extends Activity {

	//String Download_path = "http://goo.gl/Mfyya";
	String Download_path = "http://www.nasa.gov/images/content/206402main_jsc2007e113280_hires.jpg";
	String Download_ID = "DOWNLOAD_ID";

	SharedPreferences preferenceManager;
	DownloadManager downloadManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
		downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

		Button btnDownload = (Button)findViewById(R.id.download);
		btnDownload.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri Download_Uri = Uri.parse(Download_path);
				DownloadManager.Request request = new DownloadManager.Request(Download_Uri)
		        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
		        .setAllowedOverRoaming(false)
		        .setTitle("file nsa")
		        .setDescription("stuff ok.")
		        .setShowRunningNotification(true)
		        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "nasapic.jpg"); 				
				long download_id = downloadManager.enqueue(request);

				//Save the download id
				Editor PrefEdit = preferenceManager.edit();
				PrefEdit.putLong(Download_ID, download_id);
				PrefEdit.commit();
			}});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		registerReceiver(downloadReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		unregisterReceiver(downloadReceiver);
	}

	private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(preferenceManager.getLong(Download_ID, 0));
			Cursor cursor = downloadManager.query(query);

			if(cursor.moveToFirst()){
				int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
				int status = cursor.getInt(columnIndex);
				int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
				int reason = cursor.getInt(columnReason);

				if(status == DownloadManager.STATUS_SUCCESSFUL){
					//Retrieve the saved download id
					long downloadID = preferenceManager.getLong(Download_ID, 0);

					ParcelFileDescriptor file;
					try {
						file = downloadManager.openDownloadedFile(downloadID);
						Toast.makeText(MainActivityORG.this,
								"File Downloaded: " + file.toString(),
								Toast.LENGTH_LONG).show();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(MainActivityORG.this,
								e.toString(),
								Toast.LENGTH_LONG).show();
					}

				}else if(status == DownloadManager.STATUS_FAILED){
					Toast.makeText(MainActivityORG.this,
							"FAILED!\n" + "reason of " + reason,
							Toast.LENGTH_LONG).show();
				}else if(status == DownloadManager.STATUS_PAUSED){
					Toast.makeText(MainActivityORG.this,
							"PAUSED!\n" + "reason of " + reason,
							Toast.LENGTH_LONG).show();
				}else if(status == DownloadManager.STATUS_PENDING){
					Toast.makeText(MainActivityORG.this,
							"PENDING!",
							Toast.LENGTH_LONG).show();
				}else if(status == DownloadManager.STATUS_RUNNING){
					Toast.makeText(MainActivityORG.this,
							"RUNNING!",
							Toast.LENGTH_LONG).show();
				}
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
