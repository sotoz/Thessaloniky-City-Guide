package medcollege.namespace;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WhatToVisitActivity extends ListActivity {
	private ArrayList<String> types = new ArrayList<String>();
	Integer museums = 0;
	Integer churches = 0;
	Integer statues = 0;
	Integer monuments = 0;
	Integer defaults = 0;
	Monument mm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);

		for (int i = 0; i < Splash.ml.getSize(); i++) {
			mm = Splash.ml.getMonument(i);
			if (mm.getType().equals("Church")) {
				churches++;
			}
			if (mm.getType().equals("Museum")) {
				museums++;
			}
			if (mm.getType().equals("Monument")) {
				monuments++;
			}
			if (mm.getType().equals("Statue")) {
				statues++;
			}
			if (!mm.getType().equals("Church")
					&& !mm.getType().equals("Museum")
					&& !mm.getType().equals("Monument")
					&& !mm.getType().equals("Statue")) {
				defaults++;
			}

		}
		types.add("Churches (" + churches.toString() + ")");
		types.add("Museums (" + museums.toString() + ")");
		types.add("Statues (" + statues.toString() + ")");
		types.add("Monuments (" + monuments.toString() + ")");
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

			}
		});
		return lv;
	}

}
