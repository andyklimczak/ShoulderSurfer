package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.os.IBinder;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

public class MyService extends Service
{
  private Camera c;
  private static final String TAG = "myservice";
  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
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

  private Camera.FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {
    @Override
    public void onFaceDetection(Camera.Face[] faces,Camera c) {
      if (faces.length == 0) {
        Log.i(TAG, "No faces detected");
      } else if (faces.length > 0) {
        Log.i(TAG, "Faces Detected = " +
                String.valueOf(faces.length));
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

class DummySurfaceHolder implements SurfaceHolder {

  private static final int MAGIC_NUMBER = 1;

  @Override
  public Surface getSurface() {
    return new Surface(new SurfaceTexture(MAGIC_NUMBER));
  }

  @Override
  public void addCallback(Callback callback) {
    // do nothing
  }

  @Override
  public void removeCallback(Callback callback) {
    // do nothing
  }

  @Override
  public boolean isCreating() {
    return false;
  }

  @Override
  public void setType(int type) {
    // do nothing
  }

  @Override
  public void setFixedSize(int width, int height) {
    // do nothing
  }

  @Override
  public void setSizeFromLayout() {
    // do nothing
  }

  @Override
  public void setFormat(int format) {
    // do nothing
  }

  @Override
  public void setKeepScreenOn(boolean screenOn) {
    // do nothing
  }

  @Override
  public Canvas lockCanvas() {
    return null;
  }

  @Override
  public Canvas lockCanvas(Rect dirty) {
    return null;
  }

  @Override
  public void unlockCanvasAndPost(Canvas canvas) {
    // do nothing
  }

  @Override
  public Rect getSurfaceFrame() {
    return null;
  }
}