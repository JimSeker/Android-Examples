package edu.cs4730.sqlitedemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class myDBContentProvider extends ContentProvider {

	/*
	 * used the following for a lot of information
	 * http://thinkandroid.wordpress.com/2010/01/13/writing-your-own-contentprovider/
	 * 
	 * We could use the ScoreDatabase, but it is not very generic and we need to create
	 * generic methods to handle some of the requests, instead just creating them here.
	 * Plus need  to add stuff for the loader classes as well.
	 * 
	 * As a note, this provides a nearly generic content provider for any database.
	 * specific database names, columns would need to be changed, but otherwise, should
	 * work for most databases.
	 */
	
	public static final String PROVIDER_NAME = "edu.cs4730.scoreprovider";

	public static final Uri CONTENT_URI = 
			Uri.parse("content://"+ PROVIDER_NAME + "/score");

    private static final int SCORE = 1;
    private static final int SCORE_ID = 2;   
    
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "score", SCORE);
		uriMatcher.addURI(PROVIDER_NAME, "score/#", SCORE_ID);
	}

	static final String TAG = "myDBCP";
	
	//the database to be used in the contenProvider
	mySQLiteHelper myDB;
	

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		// get all rows
		case SCORE:
			return "vnd.android.cursor.dir/vnd.cs4730.score";
			// get a particular row
		case SCORE_ID:
			return "vnd.android.cursor.item/vnd.cs4730.score";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
	@Override
	public boolean onCreate() {
		myDB = new mySQLiteHelper(getContext());
		return myDB != null;
	}

	/*
	 * So delete is a simple function
	 * 
	 * example here, we are just provider a wrapper to the delete function.
	 * db.delete(mySQLiteHelper.DATABASE_TABLE, selection, selectionArgs);
	 * would looke like:
	 * "delete from " + tableName + " where " + selection + " ? " + selectionArgs"
	 *  and a fix where they are deleted by score/#, so providing _id and number. 
	 *  
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
	      SQLiteDatabase db = myDB.getWritableDatabase();
	        switch (uriMatcher.match(uri)) {
	            case SCORE:
	                break;
	            case SCORE_ID:
	                selection = selection + "_id = " + uri.getLastPathSegment();
	                break;
	            default:
	                throw new IllegalArgumentException("Unknown URI " + uri);
	        }
	 
	        int count = db.delete(mySQLiteHelper.DATABASE_TABLE, selection, selectionArgs);
	        getContext().getContentResolver().notifyChange(uri, null);
	        return count;
	}
	/*
	 * 	For the query method, but need to provide a couple more piece of information to the query
	 * but are null values, plus pass onthe sortOrder on to it. 
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
	      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	        qb.setTables(mySQLiteHelper.DATABASE_TABLE);
	 
	        switch (uriMatcher.match(uri)) {   
	            case SCORE:
	                break;
	            case SCORE_ID:
	                selection = selection + "_id = " + uri.getLastPathSegment();
	                break;
	            default:
	                throw new IllegalArgumentException("Unknown URI " + uri);
	        }
	 
	        SQLiteDatabase db = myDB.getReadableDatabase();
	        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
	 
	        c.setNotificationUri(getContext().getContentResolver(), uri);  //for notifications and loader classes if used.
	        return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != SCORE) {  //can't insert by id number, so only generic is allowed.
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
 
        if (values == null) {   //basic error checking.  values is null, provider an empty one
            values = new ContentValues();  //or we could through an SQLExecption as well.
        }
 
        SQLiteDatabase db = myDB.getWritableDatabase();
        long rowId = db.insert(mySQLiteHelper.DATABASE_TABLE, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
 
        throw new SQLException("Failed to insert row into " + uri);
	}


	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myDB.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case SCORE:  break;
            
            case SCORE_ID:  
            	selection = selection + "_id = "+uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        count = db.update(mySQLiteHelper.DATABASE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}
	


}
