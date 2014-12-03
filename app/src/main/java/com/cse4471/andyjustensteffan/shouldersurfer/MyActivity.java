/**
 * Andy Klimczak
 * Steffan Pease
 * Justen Meyer
 * cse 4471 20141203
 *
 * The is a android security app that helps prevent shoulder surfing. The app works by creating a background service the turns
 * on the camera, and the camera then scans for faces. If the camera detects more than 1 faces (more than just the current phone
 * user's face), it automatically locks the screen. The is done to keep wandering eyes from seeing the display as easily.
 */

package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "myactivity";
    public static ComponentName cn=null;
    public static DevicePolicyManager mgr=null;
    private int requestCode;
    protected static final int REQUEST_ENABLE = 0;

    @Override
    //creates the main phone page that the user sees to turn the service on and off
    protected void onCreate(Bundle savedInstanceState) {
      cn=new ComponentName(this, DAClass.class);
      mgr=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_my);
      //create button and give them listeners
      Button buttonStart = (Button)findViewById(R.id.buttonStart);
      Button buttonStop = (Button)findViewById(R.id.buttonStop);
      buttonStart.setOnClickListener(this);
      buttonStop.setOnClickListener(this);

      //used to give the app admin privileges
      //admin privileges are necessary for the app to lock the phone by itself
     if (!mgr.isAdminActive(cn)) {
         Log.d(TAG, "Requesting DeviceAdmin");
         Intent intent =
                 new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
         intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
         intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                 "For experimentation purposes only");
         startActivityForResult(intent,REQUEST_ENABLE);
     } else {
         Log.d(TAG, "AmAdmin");
      }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_ENABLE == requestCode) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  @Override
  public void onClick(View view) {
    //find which button is being pressed
    switch(view.getId())
    {
      //start camera detecting service
      case R.id.buttonStart:
        Log.d(TAG, "start");
        Intent startServ = new Intent(this, MyService.class);
        startService(startServ);
        break;
      //stop camera detecting service
      case R.id.buttonStop:
        Log.d(TAG, "stop");
        stopService(new Intent(this, MyService.class));
        break;
    }
  }
}
