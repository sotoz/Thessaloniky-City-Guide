package medcollege.namespace;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ListSettingsActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);

	}

}
