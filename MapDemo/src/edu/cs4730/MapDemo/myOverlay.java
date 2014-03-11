package edu.cs4730.MapDemo;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/*
 * overlayItems is used, because I already had the class... likely should have just written a new class to hold the data correctly.
 * It should convert from the strings coordinates to the geopoints in here, instead of the activity, also.
 *   Or maybe the string coor should be converted while processing the xml... yea probably...
 */

public class myOverlay extends Overlay  {

	ArrayList<myOverlayItem> mOverlays;
	Context mContext;
	public myOverlay( ArrayList<myOverlayItem> p, Context c) {
		mOverlays = p;
		mContext = c;
	}
	@Override
	public void draw(Canvas c, MapView mapView, boolean shadow) {
		int i;
		myOverlayItem tmp;
		Paint myColor = new Paint();
		
		myColor.setStyle(Paint.Style.FILL);
		myColor.setColor(Color.RED);
		myColor.setAlpha(50);  //not opaque 
		
		//Log.d("myItemizedOverlay","Drawing?");
		//get the projection, so I'm drawing correctly.
		Projection projection = mapView.getProjection();


		if (shadow == false) {  //draw called twice, once with shadow true and again with shadow false!
			//draw the paths here.	
			for(i=0; i<mOverlays.size(); i++) {
				Log.d("myItemizedOverlay","Drawing overlay number "+i);
				tmp = mOverlays.get(i);
				//now draw it
				ArrayList<GeoPoint> gp = tmp.getPath();
				Path p = new Path();

				Point mp = new Point();
				projection.toPixels(gp.get(0), mp);
				p.moveTo(mp.x, mp.y);
				//p.moveTo(gp.get(0).getLatitudeE6(), gp.get(0).getLongitudeE6());
				//Log.d("myItemizedOverlay","Overlay "+mp.x+" "+mp.y);
				for(int j=1; j<gp.size(); j++) {
					mp = new Point();
					projection.toPixels(gp.get(j), mp);
					p.lineTo(mp.x, mp.y);
				}
				//set the brush correctly.
				myColor.setStyle(Paint.Style.FILL);
				myColor.setColor(Color.RED);
				myColor.setAlpha(50);
				c.drawPath(p, myColor );
				//draw the outline
				myColor.setStyle(Paint.Style.STROKE);
				myColor.setStrokeWidth(3);
				myColor.setColor(Color.GRAY);
				myColor.setAlpha(50);
				c.drawPath(p, myColor );
				//reset

			}
		}
		super.draw(c, mapView, shadow);
	}
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		
		//Toast.makeText(mContext, "Geopoints: "+p.getLatitudeE6()/1E6+" "+p.getLongitudeE6()/1E6, Toast.LENGTH_LONG).show();
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle("click point");
		dialog.setMessage("Geopoints: "+p.getLongitudeE6()/1E6+" "+p.getLatitudeE6()/1E6);
		dialog.show();
		
		return false; 

		
	}
}
