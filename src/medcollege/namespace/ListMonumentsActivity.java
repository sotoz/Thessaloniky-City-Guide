package medcollege.namespace;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListMonumentsActivity extends ListActivity {
	private ArrayList<String> titles = new ArrayList<String>();
	public int position;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmonuments);

		// get current position

		for (int i = 0; i < Splash.ml.getSize(); i++) {
			Monument mm = Splash.ml.getMonument(i);
					
			titles.add(mm.getTitle()+" Distance: "+mm.getDistanceFromCurPosToString()+"(m)");
		}

		// the following method displays the monument titles with the help of a
		// ListAdapter

		displayResultList(titles);
		Log.v("Cur Longitude", Splash.curLongitude.toString());
		Log.v("Cur Latitude", Splash.curLatitude.toString());

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
					Intent intent = new Intent(ListMonumentsActivity.this,
							ViewMonumentActivity.class);

					Bundle b = new Bundle();

					b.putInt("key", position);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Splash.ml.deleteList(); //delete the monument list when the stop button gets pressed.
		super.onBackPressed();
	}
}
