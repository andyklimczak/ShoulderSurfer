package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by andy on 141019.
 */
public class MyService extends Service
{
  private static final String TAG = "myservice";
  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

  @Override
  public void onCreate()
  {
    Log.d(TAG, "started");

  }
}
