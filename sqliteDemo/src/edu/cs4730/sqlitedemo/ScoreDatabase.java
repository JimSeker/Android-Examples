package edu.cs4730.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//
//

/*
 * This an accessor class that do all the work
 * in the database.  This is the object that the 
 * system uses to access/insert/update/etc the database.
 */

public class ScoreDatabase {

	
	private mySQLiteHelper DBHelper;
	private SQLiteDatabase db;

	//constructor
	public ScoreDatabase(Context ctx) {
		DBHelper = new mySQLiteHelper(ctx);
	}

	//---opens the database---
	public void open() throws SQLException 	{
		db = DBHelper.getWritableDatabase();
	}
	
	//returns true if db is open.  Helper method.
	public boolean isOpen() throws SQLException {
		return db.isOpen();
	}
	
	//---closes the database---    
	public void close() {
		DBHelper.close();
		db.close();
	}
	
	
	 //----insert an entry -----
	  public long insertName(String name, Integer value) {
	    ContentValues initialValues = new ContentValues();
	    initialValues.put(mySQLiteHelper.KEY_NAME, name);
	    initialValues.put(mySQLiteHelper.KEY_SCORE, value);
	    return db.insert(mySQLiteHelper.DATABASE_TABLE, null, initialValues);
	  }
	  
	  //---get all the rows.
	  public Cursor getAllNames() {
	    //SELECT KEY_NAME, KEY_SCORE FROM DATABASE_TABLE SORTBY KEY_NAME; 
	      Cursor c = db.query(mySQLiteHelper.DATABASE_TABLE, 
	    		  new String[] {mySQLiteHelper.KEY_ROWID, mySQLiteHelper.KEY_NAME, mySQLiteHelper.KEY_SCORE}, 
	              null, null, null, null, 
	              mySQLiteHelper.KEY_NAME);  //sort by name.
	      if (c != null )  //make sure db is not empty!
	        c.moveToFirst();
	      return c;
	    //Cursor c = db.query(db, projection, selection, selectionArgs, null, null, orderBy);
	  }
	  //Retrieve one entry  METHOD we are supposed to use.
	  public Cursor get1name(String name) throws SQLException  {
		  //public Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, 
		  //String groupBy, String having, String orderBy, String limit)
		  //distinct 	true if you want each row to be unique, false otherwise.
		  //table 	The table name to compile the query against.
		  //columns 	A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
		  //selection 	A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
		  //selectionArgs 	You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
		  //groupBy 	A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
		  //having 	A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
		  //orderBy 	How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
		  //limit 	Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.
	      Cursor mCursor =
	              db.query(true, mySQLiteHelper.DATABASE_TABLE, 
	            		  new String[] { mySQLiteHelper.KEY_NAME, mySQLiteHelper.KEY_SCORE, 
	                  }, 
	                  mySQLiteHelper.KEY_NAME + "=\'" + name+"\'", 
	                  null,
	                  null, 
	                  null, 
	                  null, 
	                  null);
	      if (mCursor != null) {
	          mCursor.moveToFirst();
	      }
	      return mCursor;
	  }
	  //retrieve one entry, using RAW method  (ie sql statement)
	  public Cursor get1nameR(String name) {
		  //public Cursor rawQuery (String sql, String[] selectionArgs)
		  //sql 	the SQL query. The SQL string must not be ; terminated
		  //selectionArgs 	You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.
		  Cursor  mCursor = 
			  db.rawQuery("select Name, Score from HighScore where Name=\'"+name+"\'", null);
	      if (mCursor != null) {
	          mCursor.moveToFirst();
	      }
	      return mCursor;
	  }
	// ---updates a row---
	  public boolean updateRow(String name, int score) {
	  	ContentValues args = new ContentValues();
	  	args.put(mySQLiteHelper.KEY_SCORE, score);
		//returns true if one or more updates happened, otherwise false.
	  	return db.update(mySQLiteHelper.DATABASE_TABLE, args, mySQLiteHelper.KEY_NAME + "= \'" + name+"\'", null) > 0;
	  }

	  //remove all entries from the CurrentBoard
	  public void emptyName() {
	     db.delete(mySQLiteHelper.DATABASE_TABLE,null,null);
	  }
	
}
