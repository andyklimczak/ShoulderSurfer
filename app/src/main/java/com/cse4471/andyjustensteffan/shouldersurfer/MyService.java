package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
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
  //used for when the service is disabled, properly releases the camera resources
  public void onDestroy() {
    c.stopPreview();
    c.stopFaceDetection();
    c.release();
    c=null;
  }

  @Override
  //sets up the camera and starts the face detection
  public void onCreate()
  {
    mgr=MyActivity.mgr;
    cn=MyActivity.cn;
    super.onCreate();
    Log.d(TAG, "started");
    try {
      c = openFrontFacingCamera();
      c.setPreviewDisplay(new DummySurfaceHolder());//creates a dummy preview so the camera can be used in the background service
      Log.d(TAG, "before face listener");
      c.setFaceDetectionListener(faceDetectionListener);
      c.startPreview();
      c.startFaceDetection();
      Log.d(TAG, "after start of face detection");
        if (mgr.isAdminActive(cn)) {
            Log.d(TAG, "AM service admin");
        }
    }
    catch(Exception e){
      Log.d(TAG, "couldn't open front camera");
    }
  }

  //callback for the face detection
  private Camera.FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {
    @Override
    public void onFaceDetection(Camera.Face[] faces,Camera c) {
      if (faces.length == 0) {
        Log.i(TAG, "No faces detected");
      } else if (faces.length > 0) {
        Log.i(TAG, "Faces Detected = " + String.valueOf(faces.length));
          //if the number of faces seen by the camera is greater than 1, lock the camera
          if (faces.length > 1) {
              lock();
          }
      }
    }
  };

  //used to specifically get the front camera
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

    //locks the phone when the app has admin privileges
    private void lock() {
        if (mgr.isAdminActive(cn)) {
            mgr.lockNow();
        } else {
            Log.d(TAG, "Error: No admin device detected. Restart and regain admin.");
        }
    }
}