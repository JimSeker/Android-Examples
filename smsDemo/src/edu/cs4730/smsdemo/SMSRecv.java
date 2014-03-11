package edu.cs4730.smsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSRecv extends BroadcastReceiver {

	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "received something", Toast.LENGTH_SHORT).show();

		if (intent.getAction().equals(ACTION)){
			//---get the SMS message passed in--
			Bundle bundle = intent.getExtras();        
			SmsMessage[] msgs = null;
			String str = "";            
			if (bundle != null) {
				//---retrieve the SMS message received---
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];

				for (int i=0; i<msgs.length; i++){
					//get the messagePdu into our array msgs.
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					//append this message, so we can then display it.
					str += "SMS from " + msgs[i].getOriginatingAddress();                     
					str += " :";
					str += msgs[i].getMessageBody().toString();
					str += "\n";        
				}
				//---display the new SMS message---
				Toast.makeText(context, str, Toast.LENGTH_LONG).show();
			}                         
		}
	}
}
