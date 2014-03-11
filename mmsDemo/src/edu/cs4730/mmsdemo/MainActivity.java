package edu.cs4730.mmsdemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*
 * This will add a picture to a sms message and call the intent
 * for the sms message system.  The user must click send in the mms
 * app that will launch after this activity.
 * 
 * NOTE:  WILL NOT SEND A MMS MESSAGE on the emulator.  
 * It will get stuck as a draft in the messaging app.
 * Also note, doesn't work on tablets that don't have the ability to send/receive text messages.
 */

public class MainActivity extends Activity {
	Button btnSendSMS;
	EditText txtPhoneNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
		txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
		
		btnSendSMS.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {                
				String phoneNo = txtPhoneNo.getText().toString();
				            
				if (phoneNo.length()>0 )                
					sendMMS(phoneNo);                
				else
					Toast.makeText(getBaseContext(), 
						"Please enter both phone number and message.", Toast.LENGTH_SHORT).show();
			}
		});  
		
	}

	public void sendMMS(String PhoneNumber) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);  
		sendIntent.putExtra("address", PhoneNumber);
		sendIntent.putExtra("sms_body", "some text"); 
		//Note, find your own picture on the system and replace this one.
		String url = "file:///sdcard/Download/nasapic.jpg";   
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));   
		sendIntent.setType("image/png");    
		startActivity(sendIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
