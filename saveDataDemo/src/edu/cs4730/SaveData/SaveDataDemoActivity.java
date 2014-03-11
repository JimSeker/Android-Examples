package edu.cs4730.SaveData;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SaveDataDemoActivity extends Activity {
	String d1, d2, d3;
	EditText t1,t2,t3;
	TextView log;
	
    /** Called when the activity is first created. */
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        t1 = (EditText)findViewById(R.id.editText1);
        t2 = (EditText)findViewById(R.id.editText2);
        t3 = (EditText)findViewById(R.id.editText3);
        log = (TextView)findViewById(R.id.log);
        if (savedInstanceState != null) { //There is saved data
        	logthis("There is data, restoring");
        	d1 = savedInstanceState.getString("d1");
        	if (d1 != null) {
        		t1.setText(d1);
        	}
        } else {
        	logthis("No data in savedInstanceState");
        }
        getprefs();
    }
    
    @Override
    public void onPause() {
    	  super.onPause();
    	  logthis("OnPause called");
    	  // Store values between instances here
    	  SharedPreferences preferences = getSharedPreferences("example",MODE_PRIVATE);
    	  SharedPreferences.Editor editor = preferences.edit();
    	  
    	  d3 = t3.getText().toString();
    	  editor.putString("d3",d3);
    	  editor.commit();
    	  logthis("Stored preferences");
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	logthis("OnResume called");
    	getprefs();
    }
    
    /*
     * getpres() allows me to get the sharePreferences code in on place, it is called from 
     * onCreate and onPause.
     */
    void getprefs() {
    	logthis("Restoring preferences.");
    	  // Get the between instance stored values
    	SharedPreferences preferences = getSharedPreferences("example",MODE_PRIVATE);
    	  //get the key d3 and set a default value of "" if the key doesn't exist.  IE the first time this app is run.
    	  d3 = preferences.getString("d3", "");
    	  t3.setText(d3);
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	//Store the state of d1/t1 and d2/t2
    	d1 = t1.getText().toString();
    	savedInstanceState.putString("d1", d1);
    	super.onSaveInstanceState(savedInstanceState);
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