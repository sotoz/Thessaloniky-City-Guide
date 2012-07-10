package com.medcollege.thesscityguide;

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


	public Monument(Integer id, String title, String description, String image,
			String type, Double lat, Double lon, Double curLatitude, Double curLongitude
			) {
		this.title = title;
		this.id = id;
		this.type = type;
		this.description = description;
		this.image = image;
		this.lon = lon;
		this.lat = lat;
		measureDistanceLocation(curLatitude, curLongitude);
	}

	private void measureDistanceLocation(Double curLatitude, Double curLongitude) {

		// calculate the distance between the monument and the current location
		Location current = new Location("CurrentLocation");
		Location l = new Location("currentPoint");
		l.setLatitude(lat);
		l.setLongitude(lon);
		current.setLatitude(curLatitude);
		current.setLongitude(curLongitude);
		float dist = l.distanceTo(current);

		distanceFromCurPos = (double) dist;
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
