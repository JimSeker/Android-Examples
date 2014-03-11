package edu.cs4730.MapDemo;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

/*
 * Part of this code comes from http://stackoverflow.com/questions/3036139/android-drawing-path-as-overlay-on-mapview
 *  mostly the draw method, with a number of fixes.
 */

public class myItemizedOverlay extends ItemizedOverlay<myOverlayItem> {

	private ArrayList<myOverlayItem> mOverlays = new ArrayList<myOverlayItem>();
	private Context mContext;

	public myItemizedOverlay(Drawable defaultMarker, Context context)
	{
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public void addOverlay(myOverlayItem overlay)
	{
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected myOverlayItem createItem(int i)
	{
		return mOverlays.get(i);
	}

	@Override
	public int size()
	{
		return mOverlays.size();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#hitTest(com.google.android.maps.OverlayItem, android.graphics.drawable.Drawable, int, int)
	 * 
	 * Need to figure out if the user managed to tap inside the space of the area, except I don't have time right now.
	 * 
	 */
	/*
	@Override
	protected boolean hitTest(myOverlayItem item, android.graphics.drawable.Drawable marker, int hitX, int hitY) {
		return false;
		
	}
	*/
	@Override
	public void draw(Canvas c, MapView mapView, boolean shadow) {
		int i;
		myOverlayItem tmp;
		Paint myColor = new Paint();
		
		myColor.setStyle(Paint.Style.FILL);
		myColor.setColor(Color.RED);
		myColor.setAlpha(50);  //not opaque 
		
		Log.d("myItemizedOverlay","Drawing?");
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
				Log.d("myItemizedOverlay","Overlay "+mp.x+" "+mp.y);
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
	protected boolean onTap(int index)
	{
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
