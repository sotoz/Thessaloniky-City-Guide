package com.medcollege.thesscityguide;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewMonumentActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_monument);
		Bundle extras = ViewMonumentActivity.this.getIntent().getExtras();
		// get the bundle from ListMonuments activity and the alert dialog box

		try {
			if (extras != null) {
				int value = (extras.getInt("key", 0));
				getSelect(value);
			}

		} catch (Exception e) {
			Log.v("ViewMonument", "exception =" + e);
		}
	}

	private void getSelect(int val) {
		TextView tTitle = (TextView) findViewById(R.id.title);
		ImageView imageV = (ImageView) findViewById(R.id.monImage);
		TextView tType = (TextView) findViewById(R.id.type);
		TextView tMegaDesc = (TextView) findViewById(R.id.mega_desc);
		//TextView tCoords = (TextView) findViewById(R.id.coords);
		
		Monument mk = Splash.ml.returnMonFromMap(val);	
		
		tTitle.setText(mk.getTitle());
		tType.setText(mk.getType());
		tMegaDesc.setText(mk.getDescription());

		imageV.setImageResource(this.getResources().getIdentifier(
				"drawable/" + mk.getimage(), null, this.getPackageName()));
		//tCoords.setText("Coordinates : " + mk.getLon() + " + " + mk.getLat());

	}
}
