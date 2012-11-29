package com.feedbackDemo.main;

import utils.AppConstants;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.UpdateManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkForUpdates();
		registerForFeedback();
	}
	
	private void checkForCrashes() {
		CrashManager.register(this, AppConstants.APP_ID);
	}
	
	private void registerForFeedback() {
		FeedbackManager.register(this, AppConstants.APP_ID);
	}

	private void checkForUpdates() {
		/** Remove this for store builds! */
		UpdateManager.register(this, AppConstants.APP_ID);
	}
	
	private void showFeedbackActivity() {
		FeedbackManager.showFeedbackActivity(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		checkForCrashes();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main_menu, menu);
	    
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    /** Handle item selection */
	    switch (item.getItemId()) {
	        case R.id.menu_feedback:
	            showFeedbackActivity();
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
