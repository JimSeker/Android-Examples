package edu.cs4730.mapdemov2;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class BasicMapActivity extends FragmentActivity {
	  static final LatLng CHEYENNE = new LatLng(41.1400, -104.8197);  //Note, West is a negative, East is positive
	  static final LatLng KIEL = new LatLng(53.551, 9.993);
	  static final LatLng LARAMIE = new LatLng(41.312928,-105.587253);
	  private GoogleMap map;
	  
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.basicmapactivity);
		
	    map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
	            .getMap();

	        Marker kiel = map.addMarker(new MarkerOptions()
	            .position(KIEL)
	            .title("Kiel"));
	        
	        Marker laramie = map.addMarker(new MarkerOptions()
		            .position(LARAMIE)
		            .title("Laramie")
		            .snippet("I'm in Laramie!")
		            .icon(BitmapDescriptorFactory
		                .fromResource(R.drawable.ic_launcher))
	        		);
	        Marker cheyenne = map.addMarker(new MarkerOptions().position(CHEYENNE)
		            .title("Cheyenne"));
	        
	        // Move the camera instantly to hamburg with a zoom of 15.
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LARAMIE, 15));

	        // Zoom in, animating the camera.
	        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		
	     // Sets the map type to be "hybrid"
	        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); //normal map 
	        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

	        //add map click listener.
	        map.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng point) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Lat: " + point.latitude+ " Long:" +point.longitude,  Toast.LENGTH_SHORT).show();
				}
	        	
	        });			
	}
	
}
