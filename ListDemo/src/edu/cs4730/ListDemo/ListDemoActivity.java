package edu.cs4730.ListDemo;

/*
 * This example uses the following places to get info and code
 * http://www.vogella.de/articles/AndroidListView/article.html
 * http://developer.android.com/resources/tutorials/views/hello-listview.html  OK, not very helpful, but a start
 * 
 * You may find this one useful as well
 * http://bestsiteinthemultiverse.com/2009/12/android-selected-state-listview-example/
 */

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class ListDemoActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Simple.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("list1").setIndicator("Simple",
                          res.getDrawable(R.drawable.ic_tab_artists_grey))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, Simple2.class);
        spec = tabHost.newTabSpec("list2").setIndicator("Less Simple",
                          res.getDrawable(R.drawable.ic_tab_artists_grey))
                      .setContent(intent);
        tabHost.addTab(spec);
        // Do the same for the other tabs
        intent = new Intent().setClass(this, simple3.class);
        spec = tabHost.newTabSpec("list3").setIndicator("ListView",
                          res.getDrawable(R.drawable.ic_tab_artists_grey))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        // Do the same for the other tabs
        intent = new Intent().setClass(this, ActiveList.class);
        spec = tabHost.newTabSpec("list4").setIndicator("Interactive",
                          res.getDrawable(R.drawable.ic_tab_artists_grey))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        // Do the same for the other tabs
        intent = new Intent().setClass(this, Phone.class);
        spec = tabHost.newTabSpec("list5").setIndicator("Custon ListView",
                          res.getDrawable(R.drawable.ic_tab_artists_grey))
                      .setContent(intent);
        tabHost.addTab(spec);

        
        
        tabHost.setCurrentTab(0);  //first tab.
    }
}