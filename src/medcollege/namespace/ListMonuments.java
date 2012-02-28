package medcollege.namespace;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ListMonuments extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String values = getPrefs.getString("list", "4");
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

	}

}
