package edu.cs4730.VideoCap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;

public class VideoCapture extends Activity implements OnClickListener {
  boolean recording = false;
  CaptureSurface cameraView = null;
  String outputFile = "/sdcard/videoexample.mp4";
  private static final String Tag = "VideoCapture";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cameraView = (CaptureSurface) findViewById(R.id.CameraView);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      if (recording) {
        Log.d(Tag,"Calling stopRecording in CaptureSurface");
        cameraView.stopRecording();
        recording = false;
        Log.d(Tag,"finished, now calling native viewer");
        
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        Uri data = Uri.parse(outputFile);
        intent.setDataAndType(data, "video/mp4");
        startActivity(intent);
        Log.d(Tag,"Native viewer should be playing, we are done.");
        finish();
      } else {
        recording = true;
        Log.d(Tag,"Calling startRecording in CaptureSurface");
        cameraView.startRecording();
        
      }


    }

}