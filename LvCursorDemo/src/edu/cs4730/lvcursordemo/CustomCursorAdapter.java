package edu.cs4730.lvcursordemo;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomCursorAdapter extends CursorAdapter implements OnClickListener {
	
	Context myContext;
	
	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);  //0 no content observer on the cursor.  use CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER for 
		myContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		  // when the view will be created for first time,
		  // we need to tell the adapters, how each item will look
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.country_custom_info, parent, false);
		 
		return retView;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		 // here we are setting our data
		// that means, take the data from the cursor and put it in views
			 
		TextView textViewcode = (TextView) view.findViewById(R.id.codeC);
		//textViewcode.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		textViewcode.setText(cursor.getString(cursor.getColumnIndex(CntDbAdapter.KEY_CODE)));
		
		TextView textViewname = (TextView) view.findViewById(R.id.nameC);
		textViewname.setText(cursor.getString(cursor.getColumnIndex(CntDbAdapter.KEY_NAME)));

		TextView textViewcont = (TextView) view.findViewById(R.id.continentC);
		textViewcont.setText(cursor.getString(cursor.getColumnIndex(CntDbAdapter.KEY_CONTINENT)));
		
		TextView textViewregion = (TextView) view.findViewById(R.id.regionC);
		textViewregion.setText(cursor.getString(cursor.getColumnIndex(CntDbAdapter.KEY_REGION)));
		
		Button btn = (Button) view.findViewById(R.id.delC);
		btn.setTag( cursor.getString(cursor.getColumnIndex(CntDbAdapter.KEY_ROWID)) );
		btn.setOnClickListener(this);
		
	}



    @Override
    public void onClick(View view) {
        String row_id = (String) view.getTag();
  		Toast.makeText(myContext, row_id, Toast.LENGTH_SHORT).show();
	  
  		
        //onContentChanged();  //if the Database changes, notify the adapter.
  		//changeCursor (Cursor cursor)  //Change the underlying cursor to a new cursor. If there is an existing cursor it will be closed.

    }
	
}
