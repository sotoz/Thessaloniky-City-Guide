package medcollege.namespace;

import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

public class ListMonuments extends ListActivity {
	// one arrayList for the ids, titles, one for the descriptions and one for
	// the
	// images
	private ArrayList<String> ids = new ArrayList<String>();
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String> descriptions = new ArrayList<String>();
	private ArrayList<String> images = new ArrayList<String>();

	public int position;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);
		// SharedPreferences getPrefs =
		// PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		// String values = getPrefs.getString("list", "4");
		// boolean buzzmode = getPrefs.getBoolean("checkboxBuzzmode", false);

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
			Cursor c = thessDB.rawQuery(
					"SELECT _id,title, desc, image FROM MONUMENTS", null);

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String monumentId = c
								.getString(c.getColumnIndex("_id"));
						String monumentTitle = c.getString(c
								.getColumnIndex("title"));
						ids.add(monumentId);
						titles.add(monumentTitle);

					} while (c.moveToNext());
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		myDbHelper.close();
		// the following method displays the monument titles with the help of a
		// ListAdapter
		displayResultList(titles);

	}

	private ListView displayResultList(ArrayList<String> titles) {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titles));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setClickable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {
					Intent intent = new Intent(ListMonuments.this,
							ViewMonument.class);

					Bundle b = new Bundle();

					b.putInt("key", Integer.parseInt(getId(position)));
					intent.putExtras(b);
					startActivity(intent);
				} catch (IndexOutOfBoundsException ie) {
					ie.printStackTrace();
					Toast t = Toast.makeText(getApplicationContext(),
							(CharSequence) ie, 3000);
					t.show();
				}

			}
		});
		return lv;
	}

	private void showAlertDialog(final int position) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View addView = inflater.inflate(R.layout.view_alert_monument, null);
		ImageView image = (ImageView) addView.findViewById(R.id.monImage);
		String t = getImage(position);
		image.setImageResource(getImageId(this, t));

		new AlertDialog.Builder(this)
				.setTitle(getTitle(position))
				.setView(addView)
				.setMessage(getDesc(position))
				.setPositiveButton("View Details",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								Intent intent = new Intent(ListMonuments.this,
										ViewMonument.class);
								Bundle b = new Bundle();
								// int a = Integer.parseInt(getId(position));
								b.putInt("key",
										Integer.parseInt(getId(position)));
								intent.putExtras(b);
								startActivity(intent);
							}
						})
				.setNegativeButton("Back",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// ignore, just dismiss
							}
						}).show();
	}

	private String getId(int position) {
		String t = ids.get(position);
		return t;
	}

	private String getTitle(int position) {
		String t = titles.get(position);
		return t;
	}

	private String getDesc(int position) {
		String t = descriptions.get(position);
		return t;
	}

	public String getImage(int position) {
		String t = images.get(position);
		return t;
	}

	public static int getImageId(Context context, String imageName) {
		return context.getResources().getIdentifier("drawable/" + imageName,
				null, context.getPackageName());
	}
}
