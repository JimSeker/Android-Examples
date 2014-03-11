package edu.cs4730.sqlitedemo2;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends FragmentActivity {
	Cursor cursor;
	private SimpleCursorAdapter dataAdapter;
	
	//database columns
	public static final String KEY_NAME = "Name";
	public static final String KEY_SCORE = "Score";
	public static final String KEY_ROWID = "_id";   //required field for the cursorAdapter
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get the people URI
		 Uri CONTENT_URI = Uri.parse("content://edu.cs4730.scoreprovider/score");
		 //setup the information we want for the contentprovider.
		 String[] projection = new String[] { KEY_ROWID, KEY_NAME, KEY_SCORE};

		 //just for fun, sort return data by name, which instead of default which is _ID I think.
		 String SortOrder = KEY_SCORE;  //"column name, column name"  except only have one column name.
		 
		 //finally make the query
		// cursor = managedQuery(CONTENT_URI, projection, null, null, null);  //depreicated method, use one below.
		 cursor = getContentResolver().query(CONTENT_URI, projection, null, null, SortOrder);
     
		 //this is commented out, because better using a listview, which is what displayListView() does.
//		  if (c.moveToFirst()) {
//		 	do {
//		 		String str = "Id: " + c.getString(0);
//		 		str += "Name: " + c.getString(1);
//		 	} while (c.moveToNext());
//		 }
		 displayListView();

		 
	        findViewById(R.id.button1).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getApplicationContext(), loaderActivity.class));
				}
	        });
		
	}
	

    private void displayListView() {
    	
    	  if (cursor == null) {
    		  Log.i("CAA", "cursor is null...");
    	  }
    	  
    	  // The desired columns to be bound
    	  String[] columns = new String[] {
    			  KEY_NAME,
    			  KEY_SCORE
    	  };
    	 
    	  // the XML defined views which the data will be bound to
    	  int[] to = new int[] {
    	    R.id.name,
    	    R.id.score
    	  };
    	 
    	  // create the adapter using the cursor pointing to the desired data
    	  //as well as the layout information
    	  dataAdapter = new SimpleCursorAdapter(
    	    this, R.layout.scorelist,
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
    	 
    	   // Should really create a dialogfragment and display all the contact info here.  but I'll get to that 
    	   // when I have time.
    	   String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
    	   Toast.makeText(getApplicationContext(),
    	     name, Toast.LENGTH_SHORT).show();
    	 
    	   }
    	  });
    }
	
}
