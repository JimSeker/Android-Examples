package edu.cs4730.menudemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class menuV4 extends Activity {
	TextView label1, popup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuv4);
		label1 = (TextView) findViewById(R.id.label1);
		popup = (TextView) findViewById(R.id.label2);
		popup.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				showPopupMenu(v);
			}
		});
	}

	private void showPopupMenu(View v){
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {  //should kept this demo from force closing if run on the wrong API... I think...
			PopupMenu popupM = new PopupMenu(this, v);
			popupM.inflate(R.menu.popup);
			popupM.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Toast.makeText(getApplicationContext(), item.toString(),Toast.LENGTH_LONG).show();
					label1.append("\n you clicked "+item.toString());
					return true;
				}
			});

			popupM.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menuv4, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			label1.append("\n You clicked menu item 1");
			return true;
		case R.id.item2:
			label1.append("\n You clicked menu item 2");
			return true;
		case R.id.item3:
			label1.append("\n You clicked menu item 3");
			return true;
		case R.id.item4:
			label1.append("\n You clicked menu item 4");
			return true;
		case R.id.item5:
			label1.append("\n You clicked menu item 5");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


}
