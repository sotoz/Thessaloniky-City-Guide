package medcollege.namespace;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

public class MonumentMap extends MapActivity {
	private MapView myMapView;
	private MapController myMapController;

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
		myMapView.setSatellite(false); // apenergopoihse to sat view
		myMapController = myMapView.getController();
		myMapController.setZoom(zoomV); // arxiko zoom level setarismeno apo ta
										// settings
		myMapView.setBuiltInZoomControls(true);

		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this,
				myMapView);
		myMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass(); // if you want to display a compass
											// also
		myLocationOverlay.enableMyLocation();

		try {
			LocationManager locationManager;
			String context = Context.LOCATION_SERVICE;
			locationManager = (LocationManager) getSystemService(context);

			// xrisimopoioume to class criteria gia na dosoume sto android tin
			// epilogi an tha epileksei to gps i to sima tou diktuou gia
			// mas dosei tin thesi mas.

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
					3500);
			t.show();
		}
		// parakato dimiourgo to overlay gia ta eikonidia pou tha emfanizonte
		// ston xarti

		List<Overlay> mapOverlays = myMapView.getOverlays(); // create the
																// custom map
																// overlays
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.marker_flag_img);
		CustomItemizedOverlay museumOverlay = new CustomItemizedOverlay(
				drawable, this);

		DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(this);
		try {
			myDbHelper.createDataBase(); // this import the database that exists
											// in the assets folder
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			SQLiteDatabase thessDB = myDbHelper.openDataBase();
			Cursor c = thessDB.rawQuery(
					"SELECT lon,lat,title, desc FROM MONUMENTS", null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						int lon = c.getInt(c.getColumnIndex("lon"));
						int lat = c.getInt(c.getColumnIndex("lat"));
						String title = c.getString(c.getColumnIndex("title"));
						String desc = c.getString(c.getColumnIndex("desc"));

						// create a new point for each monument
						GeoPoint pt = new GeoPoint(lat, lon);

						OverlayItem overlayitem = new OverlayItem(pt, title,
								desc);

						museumOverlay.addOverlay(overlayitem);

					} while (c.moveToNext());
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		myDbHelper.close();

		mapOverlays.add(museumOverlay);

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
	};
}