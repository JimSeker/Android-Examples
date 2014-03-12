package com.sokar.cpreviewtest;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.nio.Buffer;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder.Callback;
import android.widget.ImageView;


public class CamSurfaceView extends ViewGroup implements SurfaceHolder.Callback{



private static final String TAG = "MySurfaceView";

private int width;
private int height;

private Size mPreviewSize;
private List<Size> mSupportedPreviewSizes;


private SurfaceView mSurfaceView;
private SurfaceHolder mHolder;

private Camera mCamera;
private int[] rgbints;

private Context con;
private ViewGroup vg;

private boolean isPreviewRunning = false; 

//private int mMultiplyColor;

public CamSurfaceView(Context context) {
    super(context);
    con = context;
    mSurfaceView = new SurfaceView(context);
    addView(mSurfaceView);
    vg = this;
    mHolder = mSurfaceView.getHolder();
    mHolder.addCallback(this);
 //   mMultiplyColor = getResources().getColor(R.color.multiply_color);
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
}

PreviewCallback mPreviewCallback = new PreviewCallback() 
{
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
	    Log.d("Camera", "Got a camera frame");
		
		//Canvas canvas = null;
		
		if (mHolder == null) {
		    return;
		}
		Log.d("Camera", "Got a camera frame2");
		try {
		    synchronized (mHolder)
		    {
		    	if(data == null)
		    	{
		    		Log.w(TAG, "DATA IS NOT REALLY NULL");
		    	}
		    	Camera.Parameters parameters = camera.getParameters();
		        Camera.Size size = parameters.getPreviewSize();
		       // rgbints = null;
		        rgbints = new int[size.width*size.height];
		        decodeYUV(rgbints, data, width, height);
		        Log.w("Jim", "We have data");
		        // draw the decoded image, centered on canvas
		        Bitmap bm = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888);
		        Canvas canvas = new Canvas(bm);
		        int canvasWidth = size.width;
		        int canvasHeight = size.height;
		        canvas.drawBitmap(rgbints, 0, width, canvasWidth-((width+canvasWidth)>>1), canvasHeight-((height+canvasHeight)>>1), width, height, false, null);
		   
		        if(con != null)
		        {
		        	ImageView captured = new ImageView(con);
		        	captured.setImageBitmap(bm);
		        	if (vg.getChildAt(1) != null) 
		        	  vg.removeView(vg.getChildAt(1));
		        	vg.addView(captured,1);
		        	vg.bringChildToFront(vg.getChildAt(1));
		        	//Thread.sleep(1000);		      	     			        	
		        }
		        mCamera.setOneShotPreviewCallback(mPreviewCallback);
		        // use some color filter
		        //canvas.drawColor(mMultiplyColor, Mode.MULTIPLY);
//		        rgbints = null;
//		        System.gc();
		    }
		}
		catch (Exception e)
		{
		    e.printStackTrace();
	    }
/*		 finally 
		 {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (canvas != null)
            {
                mHolder.unlockCanvasAndPost(canvas);
            }
		 }*/
	}
};

public void setCamera(Camera camera)
{
	mCamera = camera;
    if (mCamera != null) 
    {
        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        requestLayout();
    }
}

@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
{
	try 
	{
        if (mCamera != null) 
        {
            mCamera.setPreviewDisplay(holder);
        }
    } catch (IOException exception) 
    {
        Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
    }
	//LIKES TO THROW NULL POINTER EXCEPTION AT FIRST LINE OF TRY STATEMENT WHEN PAUSED THEN RESUMED...
    try 
 	{
    	Camera.Parameters parameters = mCamera.getParameters();
   	 	if(mPreviewSize != null) parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        requestLayout();

        mCamera.setParameters(parameters);
        mCamera.startPreview();
        mCamera.setOneShotPreviewCallback(mPreviewCallback);
     } catch (Exception exception) 
     {
         Log.e(TAG, "Exception caused by something... ", exception);
     }
}

@Override
public void surfaceCreated(SurfaceHolder holder) {
	//Tell the camera to draw to the preview referenced by the holder
	try 
	{
        if (mCamera != null) 
        {
            mCamera.setPreviewDisplay(holder);
        }
    } catch (IOException exception) 
    {
        Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
    }
}


@Override
public void surfaceDestroyed(SurfaceHolder holder) {
    synchronized (this) {
        try {
            if (mCamera != null) {
            	//Do we really need to null call back and release the camera?
            	//Or is that handled once this returns back to activity?
                //mHolder.removeCallback(this);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                isPreviewRunning  = false;
                mCamera.release();
            }
        } catch (Exception e) {
            Log.e("Camera", e.getMessage());
        }
    }
}



/**
 * Decodes YUV frame to a buffer which can be use to create a bitmap. use
 * this for OS < FROYO which has a native YUV decoder decode Y, U, and V
 * values on the YUV 420 buffer described as YCbCr_422_SP by Android
 * 
 * @param rgb
 *            the outgoing array of RGB bytes
 * @param fg
 *            the incoming frame bytes
 * @param width
 *            of source frame
 * @param height
 *            of source frame
 * @throws NullPointerException
 * @throws IllegalArgumentException
 */
public void decodeYUV(int[] out, byte[] fg, int width, int height) throws NullPointerException, IllegalArgumentException {
    int sz = width * height;
    if (out == null)
        throw new NullPointerException("buffer out is null");
    if (out.length < sz)
        throw new IllegalArgumentException("buffer out size " + out.length + " < minimum " + sz);
    if (fg == null)
        throw new NullPointerException("buffer 'fg' is null");
    if (fg.length < sz)
        throw new IllegalArgumentException("buffer fg size " + fg.length + " < minimum " + sz * 3 / 2);
    int i, j;
    int Y, Cr = 0, Cb = 0;
    for (j = 0; j < height; j++) {
        int pixPtr = j * width;
        final int jDiv2 = j >> 1;
    for (i = 0; i < width; i++) {
        Y = fg[pixPtr];
        if (Y < 0)
            Y += 255;
        if ((i & 0x1) != 1) {
            final int cOff = sz + jDiv2 * width + (i >> 1) * 2;
            Cb = fg[cOff];
            if (Cb < 0)
                Cb += 127;
            else
                Cb -= 128;
            Cr = fg[cOff + 1];
            if (Cr < 0)
                Cr += 127;
            else
                Cr -= 128;
        }
        int R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
        if (R < 0)
            R = 0;
        else if (R > 255)
            R = 255;
        int G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1) + (Cr >> 3) + (Cr >> 4) + (Cr >> 5);
        if (G < 0)
            G = 0;
        else if (G > 255)
            G = 255;
        int B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);
        if (B < 0)
            B = 0;
        else if (B > 255)
            B = 255;
        out[pixPtr++] = 0xff000000 + (B << 16) + (G << 8) + R;
    }
    }

}

private void showSupportedCameraFormats(Parameters p) {
    List<Integer> supportedPictureFormats = p.getSupportedPreviewFormats();
    Log.d(TAG, "preview format:" + cameraFormatIntToString(p.getPreviewFormat()));
    for (Integer x : supportedPictureFormats) {
        Log.d(TAG, "suppoterd format: " + cameraFormatIntToString(x.intValue()));
    }

}

private String cameraFormatIntToString(int format) {
    switch (format) {
    case PixelFormat.JPEG:
        return "JPEG";
    case PixelFormat.YCbCr_420_SP:
        return "NV21";
    case PixelFormat.YCbCr_422_I:
        return "YUY2";
    case PixelFormat.YCbCr_422_SP:
        return "NV16";
    case PixelFormat.RGB_565:
        return "RGB_565";
    default:
        return "Unknown:" + format;

        }
    }

@Override
protected void onLayout(boolean changed, int l, int t, int r, int b)
{
	   if (changed && getChildCount() > 0) {
           final View child = getChildAt(0);

           final int width = r - l;
           final int height = b - t;

           int previewWidth = width;
           int previewHeight = height;
           if (mPreviewSize != null) {
               previewWidth = mPreviewSize.width;
               previewHeight = mPreviewSize.height;
           }

           // Center the child SurfaceView within the parent.
           if (width * previewHeight > height * previewWidth) {
               final int scaledChildWidth = previewWidth * height / previewHeight;
               child.layout((width - scaledChildWidth) / 2, 0,
                       (width + scaledChildWidth) / 2, height);
           } else {
               final int scaledChildHeight = previewHeight * width / previewWidth;
               child.layout(0, (height - scaledChildHeight) / 2,
                       width, (height + scaledChildHeight) / 2);
           }
       }
}

//I, Nels, Don't really get these last couple of functions, something about setting the right size of the view based on device info
private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
    final double ASPECT_TOLERANCE = 0.1;
    double targetRatio = (double) w / h;
    if (sizes == null) return null;

    Size optimalSize = null;
    double minDiff = Double.MAX_VALUE;

    int targetHeight = h;

    // Try to find an size match aspect ratio and size
    for (Size size : sizes) {
        double ratio = (double) size.width / size.height;
        if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
        if (Math.abs(size.height - targetHeight) < minDiff) {
            optimalSize = size;
            minDiff = Math.abs(size.height - targetHeight);
        }
    }

    // Cannot find the one match the aspect ratio, ignore the requirement
    if (optimalSize == null) {
        minDiff = Double.MAX_VALUE;
        for (Size size : sizes) {
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
    }
    return optimalSize;
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // We purposely disregard child measurements because act as a
    // wrapper to a SurfaceView that centers the camera preview instead
    // of stretching it.
    final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
    final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    setMeasuredDimension(width, height);

    if (mSupportedPreviewSizes != null) {
        mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
    }
    else Log.w(TAG, "FUCKSTUFF");
}

}
