package edu.cs4730.lvcursordemo;

import android.os.Bundle;
import android.widget.TabHost;
import android.app.TabActivity;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        //Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
        
        
        //first tab
        intent = new Intent().setClass(this, simple.class);
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("list1").setIndicator("Simple",null)  //no picture used here.
                      .setContent(intent);
        tabHost.addTab(spec);

        //Second tab
        intent = new Intent().setClass(this, custom.class);
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("list2").setIndicator("Custom",null)  //no picture used here.
                      .setContent(intent);
        tabHost.addTab(spec);
        
        //Thrid tab
        intent = new Intent().setClass(this, ExpListviewActivity.class);
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("list3").setIndicator("Exp",null)  //no picture used here.
                      .setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);  //first tab.
	}
}
