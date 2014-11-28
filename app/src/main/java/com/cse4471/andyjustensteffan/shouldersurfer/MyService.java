package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service
{
  private static Camera c;
  private static final String TAG = "myservice";
  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

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

  private static Camera.FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {
    @Override
    public void onFaceDetection(Camera.Face[] faces,Camera c) {
      if (faces.length == 0) {
        Log.i(TAG, "No faces detected");
      } else if (faces.length > 0) {
        Log.i(TAG, "Faces Detected = " + String.valueOf(faces.length));
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
}