package com.sokar.cpreviewtest;


import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

//most of the code is from http://stackoverflow.com/questions/6478375/how-can-i-manipulate-the-camera-preview

public class MainActivity extends Activity {

	private CamSurfaceView mPreview;
    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Create a RelativeLayout container that will hold a SurfaceView,
        // and set it as the content of our activity.
        mPreview = new CamSurfaceView(this);
        setContentView(mPreview);
        

    }

    @Override
    protected void onResume() 
    {
        super.onResume();
        mCamera = Camera.open();
        mPreview.setCamera(mCamera);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (mCamera != null)
        {
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }
	    
	MySurfaceView cameraView1 = null;
	CamSurfaceView cameraView2 = null;
	CameraPreview cameraView3 = null;
	
	//The CameraPreview code is from android... don't work... http://developer.android.com/guide/topics/media/camera.html#considerations
	//setContentView(R.layout.main);
	//cameraView3 = (CameraPreview) findViewById(R.id.surface_camera);
	
	//setContentView(R.layout.activity_main);
	//cameraView1 = (MySurfaceView) findViewById(R.id.surface_camera);
	// cameraView2 = (CamSurfaceView) findViewById(R.id.surface_camera);


	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

