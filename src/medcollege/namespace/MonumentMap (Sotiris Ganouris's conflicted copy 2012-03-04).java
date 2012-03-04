package medcollege.namespace;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MonumentMap extends MapActivity {
	private static final int latitudeKam = 40632299;
	private static final int longitudeKam = 22951840;

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
		
		int zoomV = getPrefs.getInt("zoomValueSlider", 6);
		//String z = getPrefs.getString("zoomValue", "6");
		//int zoomV = Integer.parseInt(z);
		
		myMapView = (MapView) findViewById(R.id.mapview);

		myMapView.setSatellite(true); // energopoihse to sat view
		myMapController = myMapView.getController();
		myMapController.setZoom(zoomV); // arxiko zoom level setarismeno apo ta settings
		myMapView.setBuiltInZoomControls(true);

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

		// String provider = LocationManager.GPS_PROVIDER;
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


		// parakato dimiourgo to overlay gia ta eikonidia pou tha emfanizonte
		// ston xarti
		List<Overlay> mapOverlays = myMapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.marker_flag_img);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(
				drawable, this);

		GeoPoint kamara = new GeoPoint(latitudeKam, longitudeKam);
		// dimiourgite ena geopoint gia kathe POI pou xriazomaste.
		OverlayItem overlayitem = new OverlayItem(
				kamara,
				"Kamara",
				"One of the most characteristic monuments of Thessaloniki is the triumphal Arch of Galerius (Kamara), located north of Egnatia Street and in close proximity to the Rotunda.");
		// mporoume na emfanisoume ena minimataki otan klikarei kapios
		// pano sto ikonidio

		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);
	}

	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location argLocation) {
			// TODO Auto-generated method stub
			GeoPoint myGeoPoint = new GeoPoint(
					(int) (argLocation.getLatitude() * 1000000),
					(int) (argLocation.getLongitude() * 1000000));

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