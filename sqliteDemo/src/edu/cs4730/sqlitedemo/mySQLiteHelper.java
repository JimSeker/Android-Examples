package edu.cs4730.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* 
 * basic class to create/update the database we are using.
 * This is used in our database class. The table name and column
 * names are defined here as Constants, so they can be used else where easily.
 */

public class mySQLiteHelper extends SQLiteOpenHelper {

	public static final String KEY_NAME = "Name";
	public static final String KEY_SCORE = "Score";
	public static final String KEY_ROWID = "_id";   //required field for the cursorAdapter
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "myScore.db";
	public static final String DATABASE_TABLE = "HighScore";
	private static final int DATABASE_VERSION = 1;
	
	 // Database creation sql statement
	private static final String DATABASE_CREATE =
	        "CREATE TABLE HighScore ("+
			KEY_ROWID + " integer PRIMARY KEY autoincrement," +  //this line is required for the cursorAdapter.
			KEY_NAME+" TEXT, "+
			KEY_SCORE+" INTEGER );";
	
	mySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) { 
	 //NOTE only called when the database is initial created!
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   
		//Called when the database version changes, Remember the constant from above.
		Log.w(TAG, "Upgrading database from version " + oldVersion 
				+ " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
		onCreate(db);
	}

}
