package com.cse4471.andyjustensteffan.shouldersurfer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class cameraListenerActivity extends Activity {

    private Camera mCamera;
    private Bitmap cameraBitmap = null;
    private static final int MAX_FACES = 5;
    private static final int TAKE_PICTURE_CODE = 100;
    public int numFaces;
    // Holds the Face Detection result:
    private Camera.Face[] mFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_listener, menu);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
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

    private void Record() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_CODE);
        cameraBitmap = (Bitmap)intent.getExtras().get("data");
        detectFaces();
    }

    private void detectFaces() {
        numFaces = 0;
        if (null != cameraBitmap) {
            int width = cameraBitmap.getWidth();
            int height = cameraBitmap.getHeight();
            FaceDetector detector = new FaceDetector(width, height, cameraListenerActivity.MAX_FACES);
            FaceDetector.Face[] faces = new FaceDetector.Face[cameraListenerActivity.MAX_FACES];
            numFaces = faces.length;
        }
        if (numFaces != 0) {
            Log.d("ShoulderSurfer", Integer.toString(numFaces));
        }
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Record();
        }
    };
}
