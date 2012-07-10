package com.medcollege.thesscityguide;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Splash extends Activity {
	public static MonumentList ml = new MonumentList(); // create a new monument
														// list object
	public static Double curLatitude;
	public static Double curLongitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		getCurPosition();

		// calling the databasehelper class to access some custom methods for
		// manipulating the database
		DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(getApplicationContext());
		try {
			myDbHelper.createDataBase(); // this imports the database that
											// exists
											// in the assets folder
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			SQLiteDatabase thessDB = myDbHelper.openDataBase();
			Cursor c = thessDB
					.rawQuery(
							"SELECT * FROM MONUMENTS LEFT JOIN TYPE ON MONUMENTS.TYPE_ID=TYPE.TYPE_ID ORDER BY _ID",
							null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						Integer monumentId = c.getInt(c.getColumnIndex("_id"));
						String monumentTitle = c.getString(c
								.getColumnIndex("title"));
						String monumentDescription = c.getString(c
								.getColumnIndex("desc"));
						String monumentImage = c.getString(c
								.getColumnIndex("image"));
						double monumentLong = c.getDouble(c
								.getColumnIndex("lon"));
						double monumentLat = c.getDouble(c
								.getColumnIndex("lat"));
						String monumentType = c.getString(c
								.getColumnIndex("type_desc"));

						Monument m = new Monument(monumentId, monumentTitle,
								monumentDescription, monumentImage,
								monumentType, monumentLat, monumentLong,
								curLatitude, curLongitude);
						ml.addMonument(m);
						ml.addToMap(monumentId, m);
						
					} while (c.moveToNext());
				}
			}

			ml.sortMonuments(); // sort the monuments by distance here

		} catch (SQLException sqle) {
			throw sqle;
		} catch (NullPointerException npe) {
			Toast t = Toast.makeText(getApplicationContext(),
					"exc: " + npe.toString(), Toast.LENGTH_SHORT);
			t.show();
			throw npe;
		}
		myDbHelper.close();

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

					Intent startMainActivity = new Intent(
							"com.medcollege.thesscityguide.THESSCITYGUIDEACTIVITY");
					startActivity(startMainActivity);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	private void getCurPosition() {
		try {
			String context = Context.LOCATION_SERVICE;
			LocationManager locationManager = (LocationManager) getSystemService(context);

			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);

			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				curLatitude = location.getLatitude();
				curLongitude = location.getLongitude();
				// latint = (double) (lat * 1000000);
				// lngint = (double) (lng * 1000000);
			} else {
				curLatitude = 40.632006;
				curLongitude = 22.954095;
			}
		} catch (NullPointerException e) {
			Toast t = Toast.makeText(getApplicationContext(),
					"exc" + e.toString(), Toast.LENGTH_SHORT);
			t.show();
			Log.e("exception", e.toString());
		}
	}
}
