package edu.cs4730.MapDemo;

/*
 * NOTE: The key used won't work, you will need to generate a the correct key based on your keystore.
 * See the directions in the website listed below, to create a key to use.
 * 
 * This example is take from http://codemagician.wordpress.com/2010/05/06/android-google-mapview-tutorial-done-right/
 * Minor modifications were made to show laramie wyoming and conversion of Lat/Long to Geopoint E6 formats.
 * 	Which was from http://stackoverflow.com/questions/5625386/convert-wgs-84-to-lat-long/5625462#5625462
 */

import java.util.List;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class HelloGoogleMaps2 extends MapActivity {
	 @Override
	 public void onCreate(Bundle savedInstanceState)
	 {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.tab1);

	 MapView mapView = (MapView) findViewById(R.id.mapview);
	 mapView.setBuiltInZoomControls(true);

	 List<Overlay> mapOverlays = mapView.getOverlays();
	 Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
	 HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable,this);
	
	 //laramie, is geo:41.312928,-105.587253
	 //we need this in 1E6 format...
	 GeoPoint point = new GeoPoint(
			 (int) (41.312927 * 1E6), 
	            (int) (-105.587251 * 1E6)
			 );
	 /*  //for general conversations, use this formation
	  GeoPoint location = new GeoPoint(
            (int) (mLocation.getLatitude()) * 1E6), 
            (int) (mLocation.getLongitude()) * 1E6)
                     );

	  */
	 //GeoPoint point = new GeoPoint(30443769,-91158458);
	 OverlayItem overlayitem = new OverlayItem(point, "Howdy!", "I'm in Laramie!");

	 GeoPoint point2 = new GeoPoint(17385812,78480667);
	 OverlayItem overlayitem2 = new OverlayItem(point2, "Namashkaar!", "I'm in Hyderabad, India!");

	 itemizedoverlay.addOverlay(overlayitem);
	 itemizedoverlay.addOverlay(overlayitem2);

	 mapOverlays.add(itemizedoverlay);
	 }
	 @Override
	 protected boolean isRouteDisplayed()
	 {
	 return false;
	 }
}