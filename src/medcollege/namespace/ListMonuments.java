package medcollege.namespace;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class ListMonuments extends ListActivity{
	private final String DB_NAME = "thesscityguide";
	private final String TABLE_NAME = "monuments";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		//String values = getPrefs.getString("list", "4");
		boolean buzzmode = getPrefs.getBoolean("checkboxBuzzmode", false);
		// default setting = false gia to buzzmode otan ginete install h
		// efarmogi kai den exei ginei set.

		TextView buzzmodeVal, valueText;
		valueText = (TextView) findViewById(R.id.valueTexts);
		buzzmodeVal = (TextView) findViewById(R.id.buzzModeTextView);
		if (buzzmode) {
			buzzmodeVal.setText("buzzmode is on");
		} else {
			buzzmodeVal.setText("buzzmode is off");
		}
		//valueText.setText(values);
		
        ArrayList<String> results = new ArrayList<String>();
        SQLiteDatabase thessDB = null;
        
        try {
        	thessDB =  this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        	//thessDB.execSQL("DROP TABLE " + TABLE_NAME +";");
        	thessDB.execSQL("CREATE TABLE IF NOT EXISTS " +
        			TABLE_NAME +
        			" (MonumentTitle VARCHAR, Description VARCHAR," +
        			" Address VARCHAR, Lon INT(3), Lat INT(3));");
        	
        	thessDB.execSQL("INSERT INTO " +
        			TABLE_NAME +
        			" Values ('Kamara','lorem ipsum dolor sit amet','Egnatias 22',333,444);");
        	thessDB.execSQL("INSERT INTO " +
        			TABLE_NAME +
        			" Values ('Mouseio','mpla mpal mpla','kouzani',253,666);");
        	thessDB.execSQL("INSERT INTO " +
        			TABLE_NAME +
        			" Values ('plateia','foo bar bar foo','sotiris',201,202);");
        	
        	Cursor c = thessDB.rawQuery("SELECT MonumentTitle, Description FROM " +
        			TABLE_NAME, null);
        	
        	//LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	
        	//View v = vi.inflate(R.layout.row, null);

        	if (c != null ) {
        		if  (c.moveToFirst()) {
        			do {
        				String MonumentTitle = c.getString(c.getColumnIndex("MonumentTitle"));
        				String Description = c.getString(c.getColumnIndex("Description"));
        				results.add("" + MonumentTitle + ",Description: " + Description);
        				//TextView tt = (TextView) v.findViewById(R.id.toptext);
        				valueText.setText("Monument Title: " + MonumentTitle);
        			}while (c.moveToNext());
        		} 
        	}
        	//valueText.setText(results);
        	//this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.,results));

            
        } catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
        	if (thessDB != null)        		
        		thessDB.close();
        }


	}

}
