package edu.cs4730.MapDemo;

import java.util.ArrayList;

import android.graphics.Path;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class myOverlayItem extends OverlayItem{
	ArrayList<GeoPoint> myPath = null;
	
	public myOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}

	public myOverlayItem(ArrayList<GeoPoint> path, String title, String snippet) {
		super(path.get(0), title, snippet);
		myPath = path;
	}
	public ArrayList<GeoPoint> getPath() {
		return myPath;
	}
	public void setPath(ArrayList<GeoPoint> path) {
		myPath = path;
	}
}
