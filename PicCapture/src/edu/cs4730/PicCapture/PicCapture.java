package edu.cs4730.PicCapture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PicCapture extends Activity implements OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback {
  SurfaceView cameraView;
  SurfaceHolder surfaceHolder;
  Camera camera;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    cameraView = (SurfaceView) this.findViewById(R.id.CameraView);
    
    surfaceHolder = cameraView.getHolder();
    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    surfaceHolder.addCallback(this);
    cameraView.setFocusable(true);
    cameraView.setFocusableInTouchMode(true);
    cameraView.setClickable(true);
    cameraView.setOnClickListener(this);
  }
  public void onClick(View v) {
    camera.takePicture(null, null, null, this);
  }

  public void onPictureTaken(byte[] data, Camera camera) {
    Uri imageFileUri =
      getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
    try {
      OutputStream imageFileOS =
        getContentResolver().openOutputStream(imageFileUri);
      imageFileOS.write(data);
      imageFileOS.flush();
      imageFileOS.close();
    } catch (FileNotFoundException e) {
      Toast t = Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT);
      t.show();
    } catch (IOException e) {
      Toast t = Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT);
      t.show();
    }
    camera.startPreview();
  }
  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    camera.startPreview();
  }
  public void surfaceCreated(SurfaceHolder holder) {
  
    camera = Camera.open();
    if (camera == null) {
    	int i = Camera.getNumberOfCameras();
    	Log.d("jim", "camera is null, num is "+i);
    	if (i>0) {
    		camera = Camera.open(i-1);
    	}
    } else {
    	Log.d("jim", "camera is not null");
    }
    
    
    try {
      camera.setPreviewDisplay(holder);
      Camera.Parameters parameters = camera.getParameters();
      /*     if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
        parameters.set("orientation", "portrait");
        // For Android Version 2.2 and above
        camera.setDisplayOrientation(90);  //assuming holding it slide ways.
        //camera.setDisplayOrientation(0);
        // For Android Version 2.0 and above
        parameters.setRotation(90);  //assuming holding phone slide ways
        //parameters.setRotation(0);
      }
      // Effects are for Android Version 2.0 and higher

      List<String> colorEffects = parameters.getSupportedColorEffects();
      Iterator<String> cei = colorEffects.iterator();
      while (cei.hasNext())
      {
        String currentEffect = cei.next();
        if (currentEffect.equals(Camera.Parameters.EFFECT_SOLARIZE))
        {
          parameters.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
          break;
        }
      }
      */
      // End Effects for Android Version 2.0 and higher
      camera.setParameters(parameters);
    }
    catch (IOException exception)
    {
      camera.release();
    }
  }
  public void surfaceDestroyed(SurfaceHolder holder) {
    camera.stopPreview();
    camera.release();
  }

}