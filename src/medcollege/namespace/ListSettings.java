package medcollege.namespace;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ListSettings extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		//sp.registerOnSharedPreferenceChangeListener(this);
		//int zoomValueSlider = sp.getInt("zoomValueSlider", 6);
		//String zoomValueSlider2 = Integer.toString(zoomValueSlider);
	}

}
