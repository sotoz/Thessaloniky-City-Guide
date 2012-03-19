package medcollege.namespace;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

		// one arrayList for the titles and one for the descriptions
		ArrayList<String> titles = new ArrayList<String>();
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
		// the following method displays the monument titles with the help of a
		// ListAdapter
		ListView lv = displayResultList(titles, descriptions);

	}

	private ListView displayResultList(ArrayList<String> titles,
			ArrayList<String> descriptions) {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titles));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setClickable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String title = (String) parent.getItemAtPosition(position);
				showDialog(1);

			}
		});
		return lv;
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;

		dialog = getMyDialog();

		return dialog;
	}

	private Dialog getMyDialog() {
		Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.view_monument);
		dialog.setTitle("Custom Dialog");

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("Hello, this is a custom dialog!");
		ImageView image = (ImageView) dialog.findViewById(R.id.monimage);
		image.setImageResource(R.drawable.image);
		return dialog;
	}
}
