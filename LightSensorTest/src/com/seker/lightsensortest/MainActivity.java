package com.seker.lightsensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	SensorManager mySensorManager;
	Sensor myLightSensor;
	TextView textLightSensorData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView textLightSensor = (TextView)findViewById(R.id.lightsensor);
		textLightSensorData = (TextView)findViewById(R.id.lightsensordata);

		mySensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		myLightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		if (myLightSensor == null){
			textLightSensor.setText("No Light Sensor!");
		}else{
			textLightSensor.setText(myLightSensor.getName());

			mySensorManager.registerListener(lightSensorEventListener,
					myLightSensor,
					SensorManager.SENSOR_DELAY_FASTEST);
		}
	}

	SensorEventListener lightSensorEventListener = new SensorEventListener(){

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.sensor.getType()==Sensor.TYPE_LIGHT){
				textLightSensorData.setText("Light Sensor Data:" + String.valueOf(arg0.values[0]));
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
	}

}
