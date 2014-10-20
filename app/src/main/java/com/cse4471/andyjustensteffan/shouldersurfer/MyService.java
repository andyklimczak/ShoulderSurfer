package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;

/**
 * Created by andy on 141019.
 */
public class MyService extends Service
{
  private static final String TAG = "myservice";
  private Camera mCamera;
  private Bitmap cameraBitmap = null;
  private static final int MAX_FACES = 5;
  private static final int TAKE_PICTURE_CODE = 100;
  public int numFaces;
  // Holds the Face Detection result:
  private Camera.Face[] mFaces;
  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

  @Override
  public void onCreate()
  {
    Log.d(TAG, "started");
    Record();

  }

  private void Record() {
      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
      //startActivityForResult(intent, TAKE_PICTURE_CODE);  I don't know how to do this from a
      //service.
      cameraBitmap = (Bitmap)intent.getExtras().get("data");
      detectFaces();
  }

  private void detectFaces(){
      numFaces=0;
      if(null != cameraBitmap) {
          int width = cameraBitmap.getWidth();
          int height = cameraBitmap.getHeight();
          FaceDetector detector = new FaceDetector(width, height, MyService.MAX_FACES);
          Face[] faces = new Face[MyService.MAX_FACES];
          numFaces = faces.length;
      }
    if (numFaces!=0) {
        Log.d(TAG, Integer.toString(numFaces));
    }
  }

}
