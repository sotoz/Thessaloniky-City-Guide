package medcollege.namespace;

import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ListMonuments extends ListActivity {
	// one arrayList for the titles and one for the descriptions
	public ArrayList<String> titles = new ArrayList<String>();
	public ArrayList<String> descriptions = new ArrayList<String>();
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
				showAlertDialog(position);
			}
		});
		return lv;
	}

	private void showAlertDialog(int position){
	    LayoutInflater inflater=LayoutInflater.from(this);
	    View addView=inflater.inflate(R.layout.view_monument, null);
	    ImageView image = (ImageView) addView.findViewById(R.id.monImage);
	    image.setImageResource(R.drawable.image);

	    new AlertDialog.Builder(this)
	      .setTitle(getTitle(position))
	      .setView(addView)
	      .setMessage(getDesc(position))
	      .setPositiveButton("View Details",
	                          new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog,
	                              int whichButton) {
	          // ignore
	        }
	      })
	      .setNegativeButton("Cancel",
	                          new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog,
	                              int whichButton) {
	          // ignore, just dismiss
	        }
	      })
	      .show();
	}

	private String getTitle(int position){
		String t =  titles.get(position);
		return t;
	}
	private String getDesc(int position){
		String t = descriptions.get(position);
		return t;
	}
}
