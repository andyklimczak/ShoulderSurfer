ShoulderSurfer
==============

app > src > main > java > com.cse4471 > MyActivity is the turn on/off screen

app > src > main > java > com.cse4471 > MyService is where the part where the camera should count the number of faces

MyService is a background service, which runs in the background. So the current goal is to get use the service to run in the background and Log.d the number of faces the front camera sees.

https://github.com/bytefish/facerec/blob/master/android/VideoFaceDetection/app/src/main/java/org/bytefish/videofacedetection/app/CameraActivity.java

http://www.edumobile.org/android/android-programming-tutorials/face-detection-example-tutorials-in-android/

These both seem to be good resources as examples for how to use the setup.

http://stackoverflow.com/questions/4545079/lock-the-android-device-programatically  <-- this is how we lock the device, just trying to get it to print out messages when it detects faces though, because it isn't calling onfacedetect at all.
