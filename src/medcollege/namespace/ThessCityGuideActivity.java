package medcollege.namespace;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class ThessCityGuideActivity extends TabActivity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// First Tab
		intent = new Intent().setClass(this, ListMonuments.class);
		spec = tabHost
				.newTabSpec("Tab 1")
				.setIndicator("List", res.getDrawable(R.drawable.ic_menu_today))
				.setContent(intent);
		tabHost.addTab(spec);

		// Second Tab
		intent = new Intent().setClass(this, MonumentMap.class);
		spec = tabHost
				.newTabSpec("Tab 2")
				.setIndicator("Map View",
						res.getDrawable(R.drawable.ic_menu_mapmode))
				.setContent(intent);
		tabHost.addTab(spec);

		// Third Tab
		intent = new Intent().setClass(this, ListSettings.class);
		spec = tabHost
				.newTabSpec("Tab 3")
				.setIndicator("Settings",
						res.getDrawable(R.drawable.ic_menu_manage))
				.setContent(intent);
		spec.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}