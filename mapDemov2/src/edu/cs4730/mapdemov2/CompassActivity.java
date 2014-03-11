package edu.cs4730.mapdemov2;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class CompassActivity extends FragmentActivity {
	static final LatLng CHEYENNE = new LatLng(41.1400, -104.8197);  //Note, West is a negative, East is positive
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	static final LatLng LARAMIE = new LatLng(41.312928,-105.587253);
	private GoogleMap map;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.compassactivity);


		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2))
				.getMap();

		// Move the camera instantly to laramie and zoom in .
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(LARAMIE, 15));

		// Zoom in, animating the camera.
		//map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

		// Sets the map type to be "hybrid"
		//map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

	}

}
