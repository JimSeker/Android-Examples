package edu.cs4730.preferencedemo;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PrefupdateActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    private EditTextPreference mEditTextPreference;
    private ListPreference mListPreference;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.prefupdate);
			
			//NOTE the rest of this code in not necessary, used so you can display current value on the summary line.
			
	        // Get a reference to the preferences, so we can dynamically update the preference screen summary info.
			mEditTextPreference = (EditTextPreference)getPreferenceScreen().findPreference("textPref");
	        mListPreference = (ListPreference)getPreferenceScreen().findPreference("list_preference");

		}else{ 
			Log.d("pref", "api13+");
			getFragmentManager().beginTransaction().replace(android.R.id.content,
					new PrefupdateFrag()).commit();
		}
		

	}
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        // Setup the initial values
        mEditTextPreference.setSummary( "Text is " + mEditTextPreference.getSharedPreferences().getString("textPref", "Default"));
        mListPreference.setSummary("Current value is " + mListPreference.getSharedPreferences().getString("list_prefrence", "Default")); 

        // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("textPref")) {  //where textPref is the key used in the xml.
        	mEditTextPreference.setSummary( "Text is " + sharedPreferences.getString("textPref", "Default"));
        }  else if (key.equals("list_prefrence")) {
        	
          mListPreference.setSummary("Current value is " + sharedPreferences.getString(key, "Default")); 
        }


		
	}


}
