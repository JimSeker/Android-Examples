package edu.cs4730.spk2txtDemo2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.util.Log;

/* 
 * This example shows the speech recognition without a dialog box.
 *   You should likely create your own, so people know when to speak and when it stops.
 *   This works for testing, but all the Log.i should likely be removed for real applications.
 *   
 */

public class Speech2textdemo2Activity extends Activity implements OnClickListener {
	private TextView log;
	private SpeechRecognizer sr;
	private static final String TAG = "spk2txtD2";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button speakButton = (Button) findViewById(R.id.button1);    
		log = (TextView) findViewById(R.id.log);     
		speakButton.setOnClickListener(this);
		//get the SpeechRecognizer and set a listener for it.
		sr = SpeechRecognizer.createSpeechRecognizer(this);       
		sr.setRecognitionListener(new listener());        
	}

	@Override
	public	void finish() {
		sr.destroy();
		sr = null;
		super.finish();
	}
	/*
	 * The Recognitionlistener for the SpeechRecognizer.
	 */
	class listener implements RecognitionListener	{
		public void onReadyForSpeech(Bundle params)	{
			Log.d(TAG, "onReadyForSpeech");
		}
		public void onBeginningOfSpeech(){
			Log.d(TAG, "onBeginningOfSpeech");
		}
		public void onRmsChanged(float rmsdB){
			Log.d(TAG, "onRmsChanged");
		}
		public void onBufferReceived(byte[] buffer)	{
			Log.d(TAG, "onBufferReceived");
		}
		public void onEndOfSpeech()	{
			Log.d(TAG, "onEndofSpeech");
		}
		public void onError(int error)	{
			Log.d(TAG,  "error " +  error);
			logthis("error " + error);
		}
		public void onResults(Bundle results) {
			
			Log.d(TAG, "onResults " + results);
			// Fill the list view with the strings the recognizer thought it could have heard, there should be 5, based on the call
			ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
	          //display results.
			logthis("results: "+String.valueOf(matches.size())); 
			for (int i = 0; i < matches.size(); i++) {
				Log.d(TAG, "result " + matches.get(i));
				logthis("result " +i+":"+ matches.get(i));
			}

		}
		public void onPartialResults(Bundle partialResults)
		{
			Log.d(TAG, "onPartialResults");
		}
		public void onEvent(int eventType, Bundle params)
		{
			Log.d(TAG, "onEvent " + eventType);
		}
	}
	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			//get the recognize intent
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			//Specify the calling package to identify your application
			intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getClass().getPackage().getName());
			//Given an hint to the recognizer about what the user is going to say
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			//specify the max number of results
			intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
			//User of SpeechRecognizer to "send" the intent.
			sr.startListening(intent);
			Log.i(TAG,"Intent sent");
		}
	}
	/*
	 * simple method to add the log TextView.
	 */
	public void logthis (String newinfo) {
		if (newinfo != "") {
			log.setText(log.getText() + "\n" + newinfo);
		}
	}
}