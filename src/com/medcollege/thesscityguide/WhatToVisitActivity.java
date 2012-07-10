package com.medcollege.thesscityguide;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WhatToVisitActivity extends ListActivity {
	private ArrayList<String> types = new ArrayList<String>();
	Integer museums = 0;
	Integer churches = 0;
	Integer libraries = 0;
	Integer buildings = 0;
	Integer defaults = 0;
	Monument mm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);

		for (int i = 0; i < Splash.ml.getSize(); i++) {
			mm = Splash.ml.getMonument(i);
			if (mm.getType().equals("Churches")) {
				churches++;
			}
			if (mm.getType().equals("Museums")) {
				museums++;
			}
			if (mm.getType().equals("Ancient Buildings")) {
				buildings++;
			}
			if (mm.getType().equals("Libraries")) {
				libraries++;
			}
			if (!mm.getType().equals("Churches")
					&& !mm.getType().equals("Museums")
					&& !mm.getType().equals("Ancient Buildings")
					&& !mm.getType().equals("Libraries")) {
				defaults++;
			}

		}
		types.add("Churches (" + churches.toString() + ")");
		types.add("Museums (" + museums.toString() + ")");
		types.add("Ancient Buildings (" + buildings.toString() + ")");
		types.add("Libraries (" + libraries.toString() + ")");
		types.add("Other (" + defaults.toString() + ")");

		displayResultTypes(types);
	}

	private ListView displayResultTypes(ArrayList<String> types2) {
		// TODO Auto-generated method stub
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, types2));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setClickable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Intent intent = new Intent(WhatToVisitActivity.this,
							ShowCategoryActivity.class);

					Bundle b = new Bundle();

					b.putInt("key", position);
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
