package medcollege.namespace;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListMonuments extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);
		// SharedPreferences getPrefs =
		// PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		// String values = getPrefs.getString("list", "4");
		// boolean buzzmode = getPrefs.getBoolean("checkboxBuzzmode", false);
		// default setting = false gia to buzzmode otan ginete install h
		// efarmogi kai den exei ginei set.

		ArrayList<String> titles = new ArrayList<String>(); // one arrayList for
															// the titles and
															// one for the
															// descriptions
		ArrayList<String> descriptions = new ArrayList<String>();
		TextView titleText;

		// calling the databasehelper class to access some custom methods for
		// manipulating the database
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
			Cursor c = thessDB.rawQuery("SELECT title, desc FROM MONUMENTS",
					null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String MonumentTitle = c.getString(c
								.getColumnIndex("title"));
						String Description = c.getString(c
								.getColumnIndex("desc"));
						titles.add(MonumentTitle);
						descriptions.add(Description);
					} while (c.moveToNext());
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		ListView lv = displayResultList(titles, descriptions); // this method displays the monument titles
									// with the help of a ListAdapter
		
	}

	private ListView displayResultList(ArrayList<String> titles, ArrayList<String> descriptions) {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titles));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		return lv;
	}

}
