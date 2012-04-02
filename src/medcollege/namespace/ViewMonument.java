package medcollege.namespace;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewMonument extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_monument);
		Bundle extras = ViewMonument.this.getIntent().getExtras();
		// get the bundle from ListMonuments activity and the alert dialog box

		try {
			if (extras != null) {
				int value = (extras.getInt("key", 0));
				Log.v("gia select", "id = " + value);
				getSelect(value);
			}

		} catch (Exception e) {
			Log.v("ViewMonument", "exception =" + e);
		}
	}

	private void getSelect(int id) {
		TextView tTitle = (TextView) findViewById(R.id.title);
		ImageView imageV = (ImageView) findViewById(R.id.monImage);
		TextView tType = (TextView) findViewById(R.id.type);
		TextView tMegaDesc = (TextView) findViewById(R.id.mega_desc);
		TextView tCoords = (TextView) findViewById(R.id.coords);

		DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(this);

		try {
			SQLiteDatabase thessDB = myDbHelper.openDataBase();
			Cursor c = thessDB.rawQuery("SELECT * FROM MONUMENTS WHERE _id = "
					+ id, null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						// String idz = c.getString(c.getColumnIndex("_id"));
						String monumentTitle = c.getString(c
								.getColumnIndex("title"));
						String type = c.getString(c.getColumnIndex("type_id"));
						String mega_description = c.getString(c
								.getColumnIndex("mega_desc"));
						String image = c.getString(c.getColumnIndex("image"));
						int lon = c.getInt(c.getColumnIndex("lon"));
						int lat = c.getInt(c.getColumnIndex("lat"));

						tTitle.setText(monumentTitle);
						tType.setText(type);
						tMegaDesc.setText(mega_description);

						imageV.setImageResource(this.getResources()
								.getIdentifier("drawable/" + image, null,
										this.getPackageName()));
						tCoords.setText("Coordinates : " + lon + " + " + lat);

					} while (c.moveToNext());
				}
			}

		} catch (SQLException sqle) {
			throw sqle;
		}
		myDbHelper.close();
	}
}
