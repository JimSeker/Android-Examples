package edu.cs4730.VideoCap;

import java.io.IOException;

import android.content.Context;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CaptureSurface extends SurfaceView implements SurfaceHolder.Callback {
  MediaRecorder recorder;
  SurfaceHolder holder;
  String outputFile = "/sdcard/videoexample.mp4";
  private static final String Tag = "CaptureSurface";
  
  public CaptureSurface(Context context, AttributeSet attrs) {
    super(context, attrs);
    holder = getHolder();
    holder.addCallback(this);
    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    Log.d(Tag, "Setting up recorder.");
    recorder = new MediaRecorder();
    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
    CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
    recorder.setProfile(cpHigh);
//    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//    recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
    
    recorder.setOutputFile(outputFile);
    Log.d(Tag, "finished setting up recorder.");
  }

  public void startRecording() {
    recorder.start();
    Log.d(Tag, "Recording started.");
  }
  
  public void stopRecording() {
    recorder.stop();
    recorder.release();
    Log.d(Tag, "Recording finished.");
  }
  
  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {

    recorder.setPreviewDisplay(holder.getSurface());
    if (recorder != null) {
      try {
        recorder.prepare();
      } catch (IllegalStateException e) {
        Log.e(Tag, "IllegalStateException "+ e.getMessage());
      } catch (IOException e) {
        Log.e(Tag, "IOException" + e.getMessage());
      }
    }
    
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
      recorder.release(); //likely not needed... 
  }

}
