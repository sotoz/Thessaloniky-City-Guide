package com.medcollege.thesscityguide;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MonumentMapActivity extends MapActivity {
	private MapView myMapView;
	private MapController myMapController;
	private LocationManager locationManager;

	private void kentrareTopothesia(GeoPoint centerGeoPoint) {
		myMapController.animateTo(centerGeoPoint);
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		int zoomV = getPrefs.getInt("zoomValueSlider", 16);

		myMapView = (MapView) findViewById(R.id.mapview);
		myMapView.setSatellite(false); // disable sat view
		myMapController = myMapView.getController();
		myMapController.setZoom(zoomV); // starting zoom level setarismeno apo
										// ta
										// settings
		myMapView.setBuiltInZoomControls(true);

		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this,
				myMapView);
		myMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		myLocationOverlay.enableMyLocation();

		try {

			String context = Context.LOCATION_SERVICE;
			locationManager = (LocationManager) getSystemService(context);

			// we use the criteria class to allow android to
			// define the best location provider

			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);

			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
			locationManager.requestLocationUpdates(provider, 2000, 10,
					locationListener);

			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				int latint = (int) (lat * 1000000);
				int lngint = (int) (lng * 1000000);
				// metatrepw me casts ta double
				// coords se int gia na doulepsei to
				// geopoint
				GeoPoint point = new GeoPoint(latint, lngint);
				kentrareTopothesia(point);

			} else {
				int lat = 40632006;
				int lng = 22954095;
				GeoPoint point = new GeoPoint(lat, lng);
				kentrareTopothesia(point);

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Toast t = Toast.makeText(getApplicationContext(), "No GPS Found",
					Toast.LENGTH_LONG);
			t.show();
		}
		// parakato dimiourgo to overlay gia ta eikonidia pou tha emfanizonte
		// ston xarti
		
		// create the custom map overlays
		List<Overlay> mapOverlays = myMapView.getOverlays();
		//List<Overlay> mapLibrariesOverlays = myMapView.getOverlays();
		//List<Overlay> mapChurchesOverlays = myMapView.getOverlays(); 
		//List<Overlay> mapMuseumsOverlays = myMapView.getOverlays(); 
		
		Drawable drawableChurches = this.getResources().getDrawable(
				R.drawable.churches);		
		Drawable drawableBuildings = this.getResources().getDrawable(
				R.drawable.buildings);
		Drawable drawableLibraries = this.getResources().getDrawable(
				R.drawable.libraries);
		Drawable drawableMuseums = this.getResources().getDrawable(
				R.drawable.museums);
		
		CustomItemizedOverlay ChurchesOverlay = new CustomItemizedOverlay(
				drawableChurches, this);
		CustomItemizedOverlay BuildingsOverlay = new CustomItemizedOverlay(
				drawableBuildings, this);
		CustomItemizedOverlay LibrariesOverlay = new CustomItemizedOverlay(
				drawableLibraries, this);
		CustomItemizedOverlay MuseumsOverlay = new CustomItemizedOverlay(
				drawableMuseums, this);
		
		for (int i = 0; i < Splash.ml.getSize(); i++) {
			Monument mm = Splash.ml.getMonument(i);
			int latsot = (int) (mm.getLat() * 1000000);
			int lonsot = (int) (mm.getLon() * 1000000);
			// create a new point for each monument
			GeoPoint pt = new GeoPoint(latsot, lonsot);

			String kk = mm.getDescription();
			OverlayItem overlayitem = new OverlayItem(pt, mm.getTitle(), kk);
			if (mm.getType().equals("Churches")){			
				ChurchesOverlay.addOverlay(overlayitem);
			}
			if (mm.getType().equals("Libraries")){
				LibrariesOverlay.addOverlay(overlayitem);
			}
			if (mm.getType().equals("Ancient Buildings")){
				BuildingsOverlay.addOverlay(overlayitem);
			}
			if (mm.getType().equals("Museums")){
				MuseumsOverlay.addOverlay(overlayitem);
			}
		}
		mapOverlays.add(ChurchesOverlay);
		mapOverlays.add(MuseumsOverlay);
		mapOverlays.add(LibrariesOverlay);
		mapOverlays.add(BuildingsOverlay);
	}

	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location argLocation) {
			// TODO Auto-generated method stub
			int lat = (int) (argLocation.getLatitude() * 1000000);
			int lon = (int) (argLocation.getLongitude() * 1000000);
			GeoPoint myGeoPoint = new GeoPoint(lat, lon);

			kentrareTopothesia(myGeoPoint);
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	};

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub		
		locationManager.removeUpdates(locationListener);
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Splash.ml.deleteList();
		locationManager.removeUpdates(locationListener);
		locationManager = null;
		
		super.onStop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		String provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 2000, 10,
				locationListener);
		super.onResume();
	};

}