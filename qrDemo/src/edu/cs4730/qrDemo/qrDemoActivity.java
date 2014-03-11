package edu.cs4730.qrDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * NOTE. There is no easy way to tell the simulator to use a picture
 * so you need to scan via a actual device!
 */

public class qrDemoActivity extends Activity {
	TextView log;
	Button scani, encodei, encodeii,scanii;
	EditText edti, edtii;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//
		log = (TextView)findViewById(R.id.log);
		
		//using only intents
		edti = (EditText)findViewById(R.id.edti);
		scani = (Button)findViewById(R.id.scani);
		encodei = (Button)findViewById(R.id.encodei);
		Button.OnClickListener mScan = new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				//intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");

				startActivityForResult(intent, 0);
			}
		};
		scani.setOnClickListener(mScan);
		Button.OnClickListener mEncode = new Button.OnClickListener() {
			public void onClick(View v) {
				encodeBarcode("TEXT_TYPE", edti.getText().toString());
			}
		};
		encodei.setOnClickListener(mEncode);


		//using the intentintegrator
		scanii = (Button)findViewById(R.id.scanii);
		encodeii = (Button)findViewById(R.id.encodeii);

		Button.OnClickListener scanQRCode = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(qrDemoActivity.this);
				integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
			}
		};
		scanii.setOnClickListener(scanQRCode);
		Button.OnClickListener mEncodeii = new Button.OnClickListener() {
			public void onClick(View v) {
				encodeBarcode2("TEXT_TYPE", edti.getText().toString());
			}
		};
		encodeii.setOnClickListener(mEncodeii);
	}

	//using the intents to call the XYing code.
	private void encodeBarcode(String type, String data) {
		//for other encoding types, see http://code.google.com/p/zxing/source/browse/trunk/androidtest/src/com/google/zxing/client/androidtest/ZXingTestActivity.java
		Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
		intent.putExtra("ENCODE_TYPE", type);
		intent.putExtra("ENCODE_DATA", data);
		startActivity(intent);
	}

	//encode the data, but using the IntentIntegrator
	private void encodeBarcode2(String type, String data) {
		IntentIntegrator integrator = new IntentIntegrator(qrDemoActivity.this);
		integrator.shareText(data);
	}

	//wait for result from startActivityForResult calls.
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		//code to handle the intentintegrator, then 
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			// handle scan result
			String contents = scanResult.getContents();
			if (contents != null) {
				logthis("[II] Scan result is "+ scanResult.toString());
			} else {
				logthis("[II] Scan failed or canceled");
			}

		} else if (requestCode == 0) {
			//normal intent return codes.
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
				logthis("[I] scan Result is " + contents);
				logthis("[I] scan Format is " + format);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				logthis("[I] scan cancel");
			}
		}
	}
	
	/*
	 * simple method to add the log TextView.
	 */
	public void logthis (String newinfo) {
		if (newinfo != "") {
			log.setText(log.getText() + "\n" + newinfo);
		}
	}

}