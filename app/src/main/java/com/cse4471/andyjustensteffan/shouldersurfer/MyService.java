package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{
  private static Camera c;
  private static final String TAG = "myservice";
  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

    private DevicePolicyManager mgr=null;
    private ComponentName cn=null;

  @Override
  public void onDestroy() {
    c.stopPreview();
    c.stopFaceDetection();
    c.release();
    c=null;
  }

  @Override
  public void onCreate()
  {
    mgr=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
    cn=new ComponentName(this, DeviceAdminReceiver.class);
    super.onCreate();
    Log.d(TAG, "started");
    try {
      c = openFrontFacingCamera();
      c.setPreviewDisplay(new DummySurfaceHolder());
      Log.d(TAG, "before face listener");
      c.setFaceDetectionListener(faceDetectionListener);
      c.startPreview();
      c.startFaceDetection();
      Log.d(TAG, "after start of face detection");
    }
    catch(Exception e){
      Log.d(TAG, "couldn't open front camera");
    }
  }

  private Camera.FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {
    @Override
    public void onFaceDetection(Camera.Face[] faces,Camera c) {
      if (faces.length == 0) {
        Log.i(TAG, "No faces detected");
      } else if (faces.length > 0) {
        Log.i(TAG, "Faces Detected = " + String.valueOf(faces.length));
          if (faces.length > 1) {
              lock();
          }
      }
    }
  };

  private Camera openFrontFacingCamera() {
    int cameraCount = 0;
    Camera cam = null;
    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    cameraCount = Camera.getNumberOfCameras();
    for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
      Camera.getCameraInfo(camIdx, cameraInfo);
      if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
        try {
          cam = Camera.open(camIdx);
        } catch (RuntimeException e) {
          Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
        }
      }
    }
    return cam;
  }

  private void lock() {
      if (mgr.isAdminActive(cn)) {
          mgr.lockNow();
      }
      if (mgr.isAdminActive(cn)) {
          mgr.lockNow();
      } else {
          Intent intent=
                  new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
          intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
          startActivity(intent);
      }
  }
}