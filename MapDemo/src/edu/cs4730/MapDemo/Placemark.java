package edu.cs4730.MapDemo;
/* 
 * Bases of this code comes from http://codemagician.wordpress.com/2010/05/06/android-google-mapview-tutorial-done-right/
 * 
 */
public class Placemark {

	String title;
	String description;
	String coordinates;
	String address;

	public String getTitle() {
	    return title;
	}
	public void setTitle(String title) {
	    this.title = title;
	}
	public String getDescription() {
	    return description;
	}
	public void setDescription(String description) {
	    this.description = description;
	}
	public String getCoordinates() {
	    return coordinates;
	}
	public void setCoordinates(String coordinates) {
	    this.coordinates = coordinates;
	}
	public String getAddress() {
	    return address;
	}
	public void setAddress(String address) {
	    this.address = address;
	}
}
