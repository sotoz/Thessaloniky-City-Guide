package com.medcollege.thesscityguide;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShowCategoryActivity extends ListActivity {
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<Integer> monumentIDs = new ArrayList<Integer>();
	private int value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorylist);

		Bundle extras = ShowCategoryActivity.this.getIntent().getExtras();
		// get the bundle from ListMonuments activity and the alert dialog box

		try {
			if (extras != null) {
				value = (extras.getInt("key", 0));

			}

		} catch (Exception e) {
			Log.v("ViewMonument", "exception =" + e);
		}

		if (value == 0) { // churches
			for (int i = 0; i < Splash.ml.getSize(); i++) {
				Monument mm = Splash.ml.getMonument(i);
				if (mm.getType().equals("Churches")) {
					titles.add(mm.getTitle() + " Distance: "
							+ mm.getDistanceFromCurPosToString() + "(m)");
					monumentIDs.add(mm.getId());				
				}
			}
		}
		if (value == 1) { // Museums
			for (int i = 0; i < Splash.ml.getSize(); i++) {
				Monument mm = Splash.ml.getMonument(i);
				if (mm.getType().equals("Museums")) {
					titles.add(mm.getTitle() + " Distance: "
							+ mm.getDistanceFromCurPosToString() + "(m)");
					monumentIDs.add(mm.getId());

				}
			}
		}
		if (value == 2) { // Ancient Buildings
			for (int i = 0; i < Splash.ml.getSize(); i++) {
				Monument mm = Splash.ml.getMonument(i);
				if (mm.getType().equals("Ancient Buildings")) {
					titles.add(mm.getTitle() + " Distance: "
							+ mm.getDistanceFromCurPosToString() + "(m)");
					monumentIDs.add(mm.getId());
				}
			}
		}
		if (value == 3) { // Libraries
			for (int i = 0; i < Splash.ml.getSize(); i++) {
				Monument mm = Splash.ml.getMonument(i);
				if (mm.getType().equals("Libraries")) {
					titles.add(mm.getTitle() + " Distance: "
							+ mm.getDistanceFromCurPosToString() + "(m)");
					monumentIDs.add(mm.getId());
				}
			}
		}
		displayResultTypes(titles, monumentIDs);
	}

	private ListView displayResultTypes(ArrayList<String> titles, final ArrayList<Integer> monumentIDs) {
		// TODO Auto-generated method stub
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titles));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setClickable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Intent intent = new Intent(ShowCategoryActivity.this,
							ViewMonumentActivity.class);

					Bundle b = new Bundle();
														
					int idzz = monumentIDs.get(position);
					b.putInt("key", idzz);
					
					intent.putExtras(b);
					startActivity(intent);
				} catch (IndexOutOfBoundsException ie) {
					ie.printStackTrace();
					Toast t = Toast.makeText(getApplicationContext(),
							(CharSequence) ie, Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});
		return lv;
	}
}
