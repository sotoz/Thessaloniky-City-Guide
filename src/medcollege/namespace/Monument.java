package medcollege.namespace;

import java.text.DecimalFormat;

import android.location.Location;

public class Monument {
	private String title;
	private Integer id;
	private String type;
	private double lon;
	private double lat;
	private String description;
	private String image;
	private double distanceFromCurPos;

	public Monument() {

	}

	public Monument(Integer id, String title, String description, String image,
			String type, Double lon, Double lat, Double curLatitude,
			Double curLongitude) {
		this.title = title;
		this.id = id;
		this.type = type;
		this.description = description;
		this.image = image;
		this.lon = lon;
		this.lat = lat;
		measureDistanceLocation(curLatitude, curLongitude);
	}

	@SuppressWarnings("unused")
	private void measureDistanceHarvesine(Double curLatitude,
			Double curLongitude) {

		double earthRadius = 3958.75;
		double dLat = Math.toRadians(curLatitude - lat);
		double dLng = Math.toRadians(curLongitude - lon);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(curLongitude))
				* Math.cos(Math.toRadians(curLatitude)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;
		distanceFromCurPos = dist;
	}

	private void measureDistanceLocation(Double curLatitude, Double curLongitude) {

		// Tropos me location
		Location current = new Location("CurrentLocation");
		Location l = new Location("currentPoint");
		l.setLatitude(lat);
		l.setLongitude(lon);
		current.setLatitude(curLatitude);
		current.setLongitude(curLongitude);
		float dist = l.distanceTo(current);
		/*Log.v("title",getTitle());
		Log.v("Current Lat:",curLatitude.toString());
		Log.v("Current Long:", curLongitude.toString());
		Log.v("cPosition Lat:",String.valueOf(lat));
		Log.v("cPosition Long:",String.valueOf(lon));
		Log.v("-------","------");
		*/
		// float[] results;
		// Location.distanceBetween(curLatitude, curLongitude,lat,lon, results);
		distanceFromCurPos = (double) dist;
	}

	@SuppressWarnings("unused")
	private void measureDistanceTrigonometric(Double curLatitude,
			Double curLongitude) {

		double d = (Math.pow((lat - curLatitude), 2) + Math.pow(
				(lon - curLongitude), 2));
		double dist = Math.sqrt(d);
		distanceFromCurPos = dist;
	}

	public String getTitle() {
		return title;
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public Double getLon() {
		return lon;
	}

	public Double getLat() {
		return lat;
	}

	public String getDescription() {
		return description;
	}

	public String getimage() {
		return image;
	}

	public Double getDistanceFromCurPos() {
		return distanceFromCurPos;
	}
	public String getDistanceFromCurPosToString(){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(distanceFromCurPos);
		
	}

}
