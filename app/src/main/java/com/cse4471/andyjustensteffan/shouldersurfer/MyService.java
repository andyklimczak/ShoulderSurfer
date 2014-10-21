package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;

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
    Send();
  }

  private void Send() {
      Intent intent = new Intent("custom-event-name");
      // You can also include some extra data.
      intent.putExtra("message", "This is my message!");
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }

}
/*

 */