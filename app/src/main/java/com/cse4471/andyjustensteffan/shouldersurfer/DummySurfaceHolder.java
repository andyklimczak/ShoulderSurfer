package com.cse4471.andyjustensteffan.shouldersurfer;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;

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