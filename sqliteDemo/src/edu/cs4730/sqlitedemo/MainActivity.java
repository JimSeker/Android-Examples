package edu.cs4730.sqlitedemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView label1;
	ScoreDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label1 = (TextView)findViewById(R.id.textView1);
        db = new ScoreDatabase(this);
        db.open();  //if database doesn't exist, it has now created.
        democode();
        db.close();
        
        findViewById(R.id.button1).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CursorAdapterActivity.class));
			}
        });
    }
    
    @Override
    protected void onResume() {
      super.onResume(); 
     if (db == null) 
        db = new ScoreDatabase(this);
     if (!db.isOpen())
       db.open();
    }
    @Override
    protected void onPause() {
      super.onPause();
      if (db.isOpen())
        db.close();
 
    }
    
    
    public void democode() {
    	label1.setText("Creating or Using a SQLite database\n");
    	
    	Cursor c;
    	//check to see if there is any data
    	c = db.getAllNames();

    	if (c == null) {
    		//nothing in the database ?  maybe the database query failed.
    		label1.setText(label1.getText() + "Insert data in empty db");
    		//insert data.
    		db.insertName("Jim", 3012);
    		db.insertName("Brandon", 312);
    		return;
    	}
    	//check to see if no data?
    	if (c.getCount() == 0) {
    		//no data return
    		label1.setText(label1.getText() + "Insert data in empty db\n");
    		db.insertName("Jim", 3012);
    		db.insertName("Brandon", 312);
    		return;
    	}
    	//display any data.
    	//moveToFirst() should not be needed, but just in case.
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            //0 name, 1 score
            label1.setText(label1.getText() +
            		c.getString(1) + " " + c.getInt(2) + "\n"
            		);  //note, ignoring the first column, which is the ROWID.
        }
        c.close();  //release the resources, before I use it again.
        //test on return 1 item.
        
        c = db.get1name("Jim");  //test of query for 1.
        //c = db.get1nameR("Jim");   //test of rawQuery
        if (c.getCount() == 0) {
        	label1.setText(label1.getText() + "failed on select 1 item.\n");
        } else {
            label1.setText(label1.getText() + "Select on Jim returned: " +
            		c.getString(0) + " " + c.getInt(1) + "\n"
            		);
        }
        c.close(); //release the resources
        c = null;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
