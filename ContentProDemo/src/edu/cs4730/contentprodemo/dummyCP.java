package edu.cs4730.contentprodemo;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;


public class dummyCP extends ContentProvider {
	public static final String PROVIDER_NAME = "edu.cs4730.provider";

	public static final Uri CONTENT_URI = 
			Uri.parse("content://"+ PROVIDER_NAME + "/square");

    private static final int SQUARE = 1;
    private static final int SQUARE_ID = 2;   
    
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "square", SQUARE);
		uriMatcher.addURI(PROVIDER_NAME, "square/#", SQUARE_ID);
	}

	static final String TAG = "dummyCP";

	@Override
	public boolean onCreate() {
		// Nothing to setup when created, so just return true.
		Log.d(TAG, "onCreate");
		return true;
	}


	@Override
	public String getType(Uri uri) {
		Log.d(TAG, "getType");
		switch (uriMatcher.match(uri)) {
		// get all rows
		case SQUARE:
			return "vnd.android.cursor.dir/vnd.cs4730.square";
			// get a particular row
		case SQUARE_ID:
			return "vnd.android.cursor.item/vnd.cs4730.square";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String[]  Column = new String[] { "number", "square"};
		
		Log.d(TAG, "query");
		if (uriMatcher.match(uri) == SQUARE_ID) {
			String stuff = uri.getPathSegments().get(1);
			Log.d(TAG, "stuff is :" + stuff+":"); 
			if (stuff != null) {
			  int val = Integer.parseInt(stuff);  //convert from String to int.
			  Log.d(TAG, "query val is " + val); 
			  
			  //must return a Cursor, MatrixCursor is an editable cursor.  
			  MatrixCursor myCursor = new MatrixCursor(Column);
			  myCursor.addRow(new Object[] { val, val*val});
			  return myCursor;
			} else {
				Log.d(TAG, "query val is null?"); 
			}
		} else if (uriMatcher.match(uri) == SQUARE) {
			Log.d(TAG, "query all!");
			  MatrixCursor myCursor = new MatrixCursor(Column);
			  for (int i=1; i<11; i++) {
			    myCursor.addRow(new Object[] { i, i*i});
			  }
			  return myCursor;
		} 
		Log.d(TAG, "query null...");
		//something else, just return null
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.d(TAG, "update");
		// ignore, return default
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// ignore, return default
		Log.d(TAG, "insert");
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// ignore, return default
		Log.d(TAG, "delete");
		return 0;
	}


}
