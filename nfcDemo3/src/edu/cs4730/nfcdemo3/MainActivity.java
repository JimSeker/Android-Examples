package edu.cs4730.nfcdemo3;

import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	int PICK_IMAGE=1;
	Uri[] file = null;

	TextView output;
	private NfcAdapter adapter=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		output = (TextView)findViewById(R.id.tvsend);

		adapter=NfcAdapter.getDefaultAdapter(this);

		findViewById(R.id.button1).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("*/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE);
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//adapter.setBeamPushUris(file, this);
				sendbeam();
			}
		});

	}
	void sendbeam() {
		//because the this is wrong in the button... stupid.  
		//api 16+ 4.1.2 and up...
		adapter.setBeamPushUris(file, this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (requestCode==PICK_IMAGE && resultCode==RESULT_OK) {
			// adapter.setBeamPushUris(new Uri[] {data.getData()}, this);
			file = new Uri[] {data.getData()};
			output.setText(getPath(file[0]));
		}
	}


	String getPath(Uri path) {
		String filepath = "";
		//
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(path, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		filepath = cursor.getString(columnIndex);


		cursor.close();

		Log.w("Jim", "file is "+ filepath);
		return filepath;

	}
}
