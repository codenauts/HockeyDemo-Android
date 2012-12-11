package com.feedbackDemo.main;

import java.lang.ref.WeakReference;

import utils.AppConstants;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.UpdateManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
  private Context mContext;
  private CrashManagerImplementation crashManagerImpl;
  private CrashManager crashManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mContext = this;
    registerForCrashes();
    checkForUpdates();
    registerForFeedback();
		
    Button button = (Button) findViewById(R.id.do_some_wrong_stuff_button);
    button.setOnClickListener(this);
  }

  private void registerForCrashes() {
    crashManagerImpl = new CrashManagerImplementation(new WeakReference<Activity>(MainActivity.this));
    crashManager = new CrashManager(new WeakReference<Context>(mContext));
    crashManager.register(AppConstants.APP_ID, crashManagerImpl);
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

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.do_some_wrong_stuff_button:
        TextView unexistentTextView = null;
        unexistentTextView.setText("Dummy text");
        
        break;
  
      default:
        break;
    }
  }
}
