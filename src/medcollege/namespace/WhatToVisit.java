package medcollege.namespace;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WhatToVisit extends ListActivity {
	private ArrayList<String> types = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);

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
					"SELECT * FROM TYPE", null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {

						String monumentType = c.getString(c
								.getColumnIndex("type_desc"));

						types.add(monumentType);

					} while (c.moveToNext());
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		myDbHelper.close();
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
