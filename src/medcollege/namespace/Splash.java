package medcollege.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run(){
				try {
					sleep(200);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}finally{
					Intent startMainActivity = new Intent("medcollege.namespace.THESSCULTURALGUIDEACTIVITY");
					startActivity(startMainActivity);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
