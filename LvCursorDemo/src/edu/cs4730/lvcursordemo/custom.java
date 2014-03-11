package edu.cs4730.lvcursordemo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class custom extends Activity implements Button.OnClickListener {
	private CntDbAdapter dbHelper;
	private CustomCursorAdapter dataAdapter;
	Button add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom);


		dbHelper = new CntDbAdapter(this);
		dbHelper.open();

		//Clean all data
		dbHelper.deleteAllCountries();
		//Add some data
		dbHelper.insertSomeCountries();

		//Generate ListView from SQLite Database
		displayListView();

		//add button, which will add Canada on to the list and disable itself.
		add = (Button)findViewById(R.id.addC);
		add.setOnClickListener(this);
	}

	private void displayListView() {


		dataAdapter = new CustomCursorAdapter(custom.this,dbHelper.fetchAllCountries());

		ListView listView = (ListView) findViewById(R.id.listView1C);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);


		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				// Get the cursor, positioned to the corresponding row in the result set
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);

				// Get the state's capital from this row in the database.
				String countryCode =
						cursor.getString(cursor.getColumnIndexOrThrow("code"));
				Toast.makeText(getApplicationContext(),
						countryCode, Toast.LENGTH_SHORT).show();

			}
		});

		EditText myFilter = (EditText) findViewById(R.id.myFilterC);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				dataAdapter.getFilter().filter(s.toString());
			}
		});

		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbHelper.fetchCountriesByName(constraint.toString());
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dbHelper.createCountry("CND", "Canda", "North America", "North America");

		dataAdapter.changeCursor(dbHelper.fetchAllCountries());
		add.setEnabled(false);  //since not changing the CODE, any more adds will fail in the database.
	}

}
