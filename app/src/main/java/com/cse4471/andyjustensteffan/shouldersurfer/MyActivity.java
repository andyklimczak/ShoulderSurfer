package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
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
    private ComponentName cn=null;
    private DevicePolicyManager mgr=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      cn=new ComponentName(this, DeviceAdminReceiver.class);
      mgr=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_my);
      //create button and give them listeners
      Button buttonStart = (Button)findViewById(R.id.buttonStart);
      Button buttonStop = (Button)findViewById(R.id.buttonStop);
      buttonStart.setOnClickListener(this);
      buttonStop.setOnClickListener(this);

     if (!mgr.isAdminActive(cn)) {
         Intent intent =
                 new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
         intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
         startActivity(intent);
     }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
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
      case R.id.buttonStart:
        Log.d(TAG, "start");
        startService(new Intent(this, MyService.class));
        break;
      case R.id.buttonStop:
        Log.d(TAG, "stop");
        stopService(new Intent(this, MyService.class));
        break;
    }
  }
}
