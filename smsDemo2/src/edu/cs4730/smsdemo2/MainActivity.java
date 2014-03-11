package edu.cs4730.smsdemo2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This is an example of how to use port numbers with your text messages.  But it does not always work
 * seems to be based on the hardware and OS whether it will work or not.
 * 
 * From: http://code.google.com/p/krvarma-android-samples/source/browse/trunk/SMSDemo/
 */


public class MainActivity extends Activity {
	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
	private static final int SMS_PORT = 8091;
	private static final String SMS_DELIVERED = "SMS_DELIVERED";
	private static final String SMS_SENT = "SMS_SENT";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button)findViewById(R.id.send)).setOnClickListener(onButtonClick);

		//setup the broadcast receivers for delivery and sent
		registerReceiver(sendreceiver, new IntentFilter(SMS_SENT));
		registerReceiver(deliveredreceiver, new IntentFilter(SMS_DELIVERED));
		
		registerReceiver(smsreceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));


	}
	@Override
	protected void onDestroy()	{
		unregisterReceiver(sendreceiver);
		unregisterReceiver(deliveredreceiver);
		unregisterReceiver(smsreceiver);

		super.onDestroy();
	}

	private void sendSms(String phonenumber,String message, boolean isBinary)	{
		SmsManager manager = SmsManager.getDefault();

		PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
		PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

		if(isBinary)
		{
			byte[] data = new byte[message.length()];

			for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
			{
				data[index] = (byte)message.charAt(index);
			}

			manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
		}
		else
		{
			int length = message.length();

			if(length > MAX_SMS_MESSAGE_LENGTH)
			{
				ArrayList<String> messagelist = manager.divideMessage(message);

				manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
			}
			else
			{
				manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
			}
		}
	}

	private View.OnClickListener onButtonClick = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch(v.getId())
			{
			case R.id.send:
			{
				String phonenumber = ((TextView)findViewById(R.id.phonenumber)).getText().toString();
				String message = ((TextView)findViewById(R.id.message)).getText().toString();
				boolean isBinary = ((CheckBox)findViewById(R.id.binary)).isChecked();

				sendSms(phonenumber,message, isBinary);

				break;
			}
			}
		}
	};

	private BroadcastReceiver sendreceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String info = "Send information: ";

			switch(getResultCode())
			{
			case Activity.RESULT_OK: info += "send successful"; break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE: info += "send failed, generic failure"; break;
			case SmsManager.RESULT_ERROR_NO_SERVICE: info += "send failed, no service"; break;
			case SmsManager.RESULT_ERROR_NULL_PDU: info += "send failed, null pdu"; break;
			case SmsManager.RESULT_ERROR_RADIO_OFF: info += "send failed, radio is off"; break;
			}

			Toast.makeText(getBaseContext(), info, Toast.LENGTH_SHORT).show();

		}
	};

	private BroadcastReceiver deliveredreceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String info = "Delivery information: ";

			switch(getResultCode())
			{
			case Activity.RESULT_OK: info += "delivered"; break;
			case Activity.RESULT_CANCELED: info += "not delivered"; break;
			}

			Toast.makeText(getBaseContext(), info, Toast.LENGTH_SHORT).show();
		}
	};

	private BroadcastReceiver smsreceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Bundle bundle = intent.getExtras();        
			SmsMessage[] msgs = null;

			if(null != bundle)
			{
				String info = "Text SMS from ";
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];

				for (int i=0; i<msgs.length; i++){
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
					info += msgs[i].getOriginatingAddress();                    
					info += "\n*****TEXT MESSAGE*****\n";
					info += msgs[i].getMessageBody().toString();
				}

				Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
			}                        
		}
	};

	/*  really don't need the menu.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
