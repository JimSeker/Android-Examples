package edu.cs4730.drawDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * drawDemoActivity show first how to draw and create a canvas object using an ImageView
 * It also shows some very basic animation using a thread. 
 */

public class drawDemoActivity extends Activity implements Runnable {
	TextView Label;
	ImageView theboardfield;
	Bitmap theboard;
	Canvas theboardc;
	Button btnClear,btnAClear,btnSave, btnLoad;
	final int boardsize = 480;
	boolean isAnimation = false;
	protected Handler handler;
	//for drawing
	Rect myRec;
	Paint myColor;
	Bitmap alien;
	//for the thread
	Thread myThread;
	//ImageButton ib;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Label = (TextView) findViewById(R.id.labelfield);
		btnAClear = (Button) findViewById(R.id.button1);
		btnAClear.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				startThread();  //done to simply a few things.
			}
		});
		btnClear = (Button) findViewById(R.id.button2);
		btnClear.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				theboardc.drawColor(Color.WHITE);  //background color for the board.
				drawBmp();
			}
		});		
		//get the imageview and create a bitmap to put in the imageview.
		//also create the canvas to draw on.
		theboardfield = (ImageView)findViewById(R.id.boardfield);
		theboard = Bitmap.createBitmap(boardsize, boardsize, Bitmap.Config.ARGB_8888);
		theboardc = new Canvas(theboard);
		theboardc.drawColor(Color.WHITE);  //background color for the board.
		theboardfield.setImageBitmap(theboard);
		theboardfield.setOnTouchListener(new myTouchListener());

		//For drawing
		myRec = new Rect(0,0,10,10);
		myColor = new Paint();  //default black
		myColor.setStyle(Paint.Style.FILL);
			
		//load a picture
		alien = BitmapFactory.decodeResource(getResources(), R.drawable.alien);
		//draw it on the screen.
		//theboardc.drawBitmap(alien, 300,300, myColor);
		theboardc.drawBitmap(alien,null,new Rect (0,0,300,300),myColor);
		//message handler for the animation.
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) { //redraw image
					if (theboard != null && theboardfield != null) {
						drawBmp();
					}
				} 
			}
		};
/*		ib = (ImageButton)findViewById(R.id.imageButton1);
		String stuff = ib.getContentDescription().toString();
		ib.setContentDescription("There");
		*/
		
		btnLoad = (Button) findViewById(R.id.load);
		btnLoad.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				bmpload();
			}
		});
		btnSave = (Button) findViewById(R.id.save);
		btnSave.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				bmpsave();
			}
		});
		
		
	}

	/*
	 * simple method to set the board in the imageveiw and then cause it to redraw.
	 */
	void drawBmp() {
		theboardfield.setImageBitmap(theboard);
		theboardfield.invalidate();
	}
	/*
	 * Starts up a new thread to run some animation.
	 */
	public void startThread() {
		isAnimation = true;
		//new Thread(this).start();
		myThread = new Thread(this);
		myThread.start();
	}

	@Override
	public void run() {
		boolean done = false;
		int block = boardsize /10;
		int x = 0, i =0;
		//first draw a red down the board
		myColor.setColor(Color.RED);
		while (!done) {
			if (!isAnimation) {return;}  //To stop the thread, if the system is paused or killed.
			//clear part of the board
			/*
			for (i=0; i<=block; i++) {
			  theboardc.drawLine(0, x+i, boardsize, x+i, myColor);
			}
			x += block;
			*/
			 theboardc.drawLine(0,x,boardsize,x,myColor);
			 x++;
			 
			//send message to redraw
			handler.sendEmptyMessage(0);
			//now wait a little bit.
			try {
				Thread.sleep(10);  //change to 100 for a commented out block, instead of just a line.
			} catch (InterruptedException e) {
				; //don't care
			}
			//determine if we are done or move the x?
			if (x >= boardsize) done = true;
		}
		//now draw white back up the board
		x = boardsize - block;
		done = false;
		myColor.setColor(Color.WHITE);
		while (!done) {
			if (!isAnimation) {return;}  //To stop the thread, if the system is paused or killed.
			//clear part of the board
			for (i=0; i<=block; i++) {
			  theboardc.drawLine(0, x+i, boardsize, x+i, myColor);
			}
			x -= block;
			//send message to redraw
			handler.sendEmptyMessage(0);
			//now wait a little bit.
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				; //don't care
			}
			//determine if we are done or move the x?
			if (x <0) done = true;
		}
		//now draw white back up the board
		
		myColor.setColor(Color.BLACK);
		isAnimation = false;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	public void onPause() {
		finish();
		super.onPause();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	public void finish() {
		//first make sure the thread is stopped, to prevent a force close.
		if (myThread != null) {
			isAnimation = false;
			try {
				if (myThread.isAlive()) {
				  myThread.join();
				}
			} catch (InterruptedException e) {
				; //don't care.
			}
			myThread = null;
		}
		theboard = null;
		theboardc = null;
		myColor = null;
		handler = null;
		super.finish();
	}
    class myTouchListener implements View.OnTouchListener {
    	@Override  	
    	public boolean onTouch(View v, MotionEvent event) {
    		
    		//Don't draw if there is animation going on.
    		if (isAnimation) return true;  //but say we handled (ignored) the event.
    		
    	    //We just need the x and y position, to draw on the canvas
    		//so, retrieve the new x and y touch positions
    		int x = (int) event.getX();
    		int y = (int) event.getY();
    		//now draw on our canvas
    		myRec.set(x,y,x+10,y+10);
    		theboardc.drawRect(myRec, myColor);
    		drawBmp();
    		return true;
    	}
    };
    
    void bmpload() {
    	
    	DataInputStream in;
    	//file is located in the private data section of the app, instead of on the sdcard.
    	String filename = "DrawDemo.png";
        Bitmap bmp = null;
        
		try {
			in = new DataInputStream( openFileInput(filename) ) ;
			bmp = BitmapFactory.decodeStream(in);
			//theboard = bmp;  can't draw on screen anymore...!
			theboardc.drawBitmap(bmp, 0, 0, null);
			drawBmp();
		} catch (FileNotFoundException e) {
			Log.i("bmpload", "file not found");
		} 

    }
    
    
    /*
     * Save the image drawn in a file called DrawDemo.png
     */
    void bmpsave() {
    	//Log.i("bmpsave", "SAved?");
    	String filename = "DrawDemo.png";
		DataOutputStream dos;
		//store the image in the local data directory.
		try {
			dos = new DataOutputStream( openFileOutput(filename, Context.MODE_PRIVATE));
    		
    	    // FileOutputStream out = new FileOutputStream();
    	     if (theboard.compress(Bitmap.CompressFormat.PNG, 90, dos) )
    	    	 Log.i("bmpsave", "It worked");
    	     else 
    	    	 Log.w("bmpsave", "Bmp save failed!");
    	     dos.flush();
    	     dos.close();
    	} catch (Exception e) {
    	     e.printStackTrace();
    	}

    }
}