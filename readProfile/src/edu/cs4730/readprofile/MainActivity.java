package edu.cs4730.readprofile;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button call;
	TextView output;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        findViewById(R.id.btn).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getprofile();
			}
        });
		output = (TextView) findViewById(R.id.ouput);
	}

	 public void getprofile() {
		 
		 Cursor c = getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null,  null, null, null);
		 int count = c.getCount();

		 String[] columnNames = c.getColumnNames();

		 boolean b = c.moveToFirst();
		 int position = c.getPosition();
		// if (count == 1 && position == 0) {
			 for (int i = 0; i < count; i++) {
				 for (int j = 0; j < columnNames.length; j++) {
					 output.append("\n"+
					    columnNames[j] +" " +
					    c.getString(c.getColumnIndex(columnNames[j]))
					  );
				 }	
				 boolean b2 = c.moveToNext();
			 }
		// }
		 c.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
