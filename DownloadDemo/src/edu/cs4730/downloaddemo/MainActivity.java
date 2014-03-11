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
 * http://blog.vogella.com/2011/06/14/android-downloadmanager-example/
 */


public class MainActivity extends Activity {

	//big file, takes about 30 seconds to download
	//String Download_path = "http://www.nasa.gov/images/content/206402main_jsc2007e113280_hires.jpg";
    //smaller test file.
	String Download_path = "http://www.cs.uwyo.edu/~seker/courses/2150/30mbHD.jpg";
	long download_id =-1;

	SharedPreferences preferenceManager;
	DownloadManager downloadManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		        .setTitle("file nasa")
		        .setDescription("stuff ok.")
		        .setShowRunningNotification(true)
		        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "nasapic.jpg"); 				
				download_id = downloadManager.enqueue(request);

			}});
		Button btnDownload2 = (Button)findViewById(R.id.download2);
		btnDownload2.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri Download_Uri = Uri.parse(Download_path);
				DownloadManager.Request request = new DownloadManager.Request(Download_Uri)
		        //.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
		        //.setAllowedOverRoaming(true)
		        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)  //api 11 and above!
		        //.setShowRunningNotification(true)   //api 10 and below.  Doesn't work on 4.1.1 must use above.
		        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "nasapic.jpg"); 				
				download_id = downloadManager.enqueue(request);

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
		long intentdownloadId;
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
				Bundle extras = intent.getExtras();
                intentdownloadId = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				Toast.makeText(MainActivity.this,	"should match: id is " + download_id+ " int_id is " + intentdownloadId,
						Toast.LENGTH_LONG).show();
			}   
                
			DownloadManager.Query query = new DownloadManager.Query();
			query.setFilterById(intentdownloadId);
			Cursor cursor = downloadManager.query(query);

			if(cursor.moveToFirst()){
				int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
				int status = cursor.getInt(columnIndex);
				int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
				int reason = cursor.getInt(columnReason);
				int columnFname = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
				String Fname;
				if (columnFname >=0) {
				  Fname = cursor.getString(columnFname);
				} else {
					Fname = "unknown";
				}
				if(status == DownloadManager.STATUS_SUCCESSFUL){
					
					ParcelFileDescriptor file;
					try {
						file = downloadManager.openDownloadedFile(intentdownloadId);
						Toast.makeText(MainActivity.this,
								"File Downloaded: " +Fname +" and ready to process",
								Toast.LENGTH_LONG).show();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(MainActivity.this,
								e.toString(),
								Toast.LENGTH_LONG).show();
					}

				}else if(status == DownloadManager.STATUS_FAILED){
					Toast.makeText(MainActivity.this,
							"FAILED!\n" + "reason of " + reason,
							Toast.LENGTH_LONG).show();
				}else if(status == DownloadManager.STATUS_PAUSED){
					Toast.makeText(MainActivity.this,
							"PAUSED!\n" + "reason of " + reason,
							Toast.LENGTH_LONG).show();
				}else if(status == DownloadManager.STATUS_PENDING){
					Toast.makeText(MainActivity.this,
							"PENDING!",
							Toast.LENGTH_LONG).show();
				}else if(status == DownloadManager.STATUS_RUNNING){
					Toast.makeText(MainActivity.this,
							"RUNNING!",
							Toast.LENGTH_LONG).show();
				}
			}
		}

	};


}
