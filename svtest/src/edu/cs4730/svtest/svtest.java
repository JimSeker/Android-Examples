package edu.cs4730.svtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class svtest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new mySurfaceView(this));

    }
}