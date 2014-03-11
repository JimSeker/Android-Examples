package edu.cs4730.MapDemo;
import java.util.ArrayList;


public class dataSet {
	private ArrayList<Placemark> placemarks = new ArrayList<Placemark>();
	
	public void addPlacemark(Placemark currentPlacemark) {
		placemarks.add(currentPlacemark);
	}

	public ArrayList<Placemark> getPlacemarks() {
		return placemarks;
	}

	public void setPlacemarks(ArrayList<Placemark> placemarks) {
		this.placemarks = placemarks;
	}
	public int getSize() {
		return placemarks.size();
	}
}
