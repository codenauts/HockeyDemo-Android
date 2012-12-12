package com.feedbackDemo.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.provider.Settings;
import android.widget.Toast;
import net.hockeyapp.android.CrashManagerListener;

/**
 * {@link CrashManagerListener} implementation
 * @author Bogdan Nistor
 *
 */
public class CrashManagerImplementation extends CrashManagerListener {
  private Activity activity;
  
  /**
   * Constructor
   * @param weakActivity
   */
  public CrashManagerImplementation(WeakReference<Activity> weakActivity) {
    if (weakActivity != null) {
      activity = weakActivity.get();
    }
  }
  
  public Boolean ignoreDefaultHandler() {
    return true;
  }
  
  public String getContact() {
    return "joe.user@gmail.com";
  }
  
  public String getUserID() {
    if (activity == null) {
      return null;
    }
    
    return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
  }
  
  public Boolean onCrashesFound() {
    return true;
  }
  
  public void onCrashesSent() {
    if (activity != null) {
      activity.runOnUiThread(new Runnable() {
        
        @Override
        public void run() {
          Toast.makeText(activity, "Crash data was sent. Thanks!", Toast.LENGTH_LONG).show();            
        }
      });
    }
  }
  
  public void onCrashesNotSent() {
    if (activity != null) {
      activity.runOnUiThread(new Runnable() {
        
        @Override
        public void run() {
          Toast.makeText(activity, "Crash data failed to sent. Please try again later.", Toast.LENGTH_LONG).show();
        }
      });
    }
  }
  
  public String getDescription() {
    String description = "";

    try {
      Process process = Runtime.getRuntime().exec("logcat -d HockeyApp:D *:S");
      BufferedReader bufferedReader = 
        new BufferedReader(new InputStreamReader(process.getInputStream()));

      StringBuilder log = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        log.append(line);
        log.append(System.getProperty("line.separator"));
      }
      
      bufferedReader.close();

      description = log.toString();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }

    return description;
  }
}
