package edu.cs4730.sqlitedemo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
/*
 * http://www.mysamplecode.com/2012/07/android-listview-cursoradapter-sqlite.html
 */
public class CursorAdapterActivity extends Activity {
	TextView label1;
	ScoreDatabase db;
	 private SimpleCursorAdapter dataAdapter;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursoradapteractivity);
        label1 = (TextView)findViewById(R.id.textView1);
        
        db = new ScoreDatabase(this);
        db.open();  //if database doesn't exist, it has now created.
        
        displayListView();
        //db.close();
    }

    private void displayListView() {
    	 
    	 
    	  Cursor cursor = db.getAllNames();
    	 
    	  if (cursor == null) {
    		  Log.i("CAA", "cursor is null...");
    	  }
    	  
    	  // The desired columns to be bound
    	  String[] columns = new String[] {
    	    mySQLiteHelper.KEY_NAME,
    	    mySQLiteHelper.KEY_SCORE
    	  };
    	 
    	  // the XML defined views which the data will be bound to
    	  int[] to = new int[] {
    	    R.id.name,
    	    R.id.score
    	  };
    	 
    	  // create the adapter using the cursor pointing to the desired data
    	  //as well as the layout information
    	  dataAdapter = new SimpleCursorAdapter(
    	    this, R.layout.highscore,
    	    cursor,
    	    columns,
    	    to,
    	    0);
    	 
    	  ListView listView = (ListView) findViewById(R.id.listView1);
    	  // Assign adapter to ListView
    	  listView.setAdapter(dataAdapter);
    	 
    	 
    	  listView.setOnItemClickListener(new OnItemClickListener() {
    	   @Override
    	   public void onItemClick(AdapterView<?> listView, View view,
    	     int position, long id) {
    	   // Get the cursor, positioned to the corresponding row in the result set
    	   Cursor cursor = (Cursor) listView.getItemAtPosition(position);
    	 
    	   // display the name in a toast.
    	   String name = cursor.getString(cursor.getColumnIndexOrThrow(mySQLiteHelper.KEY_NAME));
    	   Toast.makeText(getApplicationContext(),
    	     name, Toast.LENGTH_SHORT).show();
    	 
    	   }
    	  });
    }
}
