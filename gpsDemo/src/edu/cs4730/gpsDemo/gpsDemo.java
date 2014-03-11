package edu.cs4730.gpsDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.location.*;

import java.util.List;

public class gpsDemo extends Activity {
  TextView output;
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    output = (TextView) findViewById(R.id.TextView01);
    output.append("\nNOTE, if you haven't told the Sim a location, there will be errors!\n");
    LocationManager myL = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
    //In other places use (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    //add a location listener, here building on the fly.
    myL.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
        new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (location != null) {
          output.append("\n onLocationChanged called");
          /*	        location.getAltitude();
    			        location.getLatitude();
    			        location.getLongitude();
    			        location.getTime();
    			        location.getAccuracy();
    			        location.getSpeed();
    			        location.getProvider();
           */
          output.append("\n"+location.getLatitude() + " "+ location.getLongitude());

        }
      }
      @Override
      public void onProviderDisabled(String provider) {

      }
      @Override
      public void onProviderEnabled(String provider) {


      }
      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {


      }
    } );

    //Get a list of providers
    //could also use  String = getBestProvider(Criteria  criteria, boolean enabledOnly)
    List<String> mylist = myL.getProviders(true);
    Location loc = null;  String networkstr = "";
    for (int i=0; i<mylist.size() && loc ==null ; i++) {
      networkstr = mylist.get(i).toString();
      output.append("\n Attempting: "+ networkstr);
      loc = myL.getLastKnownLocation(networkstr);   
    }
    if (loc != null ) {
      double sLatitude = loc.getLatitude();
      double sLongitude = loc.getLongitude();
      String location = " "+sLatitude+","+sLongitude; 
      output.append(location);
    } else {
      output.append("\nNo location can be found.\n");
    }
  }
}
