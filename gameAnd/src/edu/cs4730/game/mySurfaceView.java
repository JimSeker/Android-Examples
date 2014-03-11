package edu.cs4730.game;

import java.util.Random;
import java.util.Vector;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class mySurfaceView extends SurfaceView implements SurfaceHolder.Callback { 
	private myThread thread;
	public Paint red, black;
	public float x, y;
	boolean running = false, gameover=false;
	boolean havefocus=true;  //deal with problems of menu and losing focus.
	int maxAliens = 5, maxShots=3;
	int height, width;
	int left, right, top, bottom;
	int score=0;
	int moveship =0; boolean tofire = false;
	Bitmap shipBmp, alien1Bmp, alien2Bmp, shotBmp,bg;
	int imgcnt=0;
	obj ship;
	//Vector shots, aliens;
	Vector<obj> shots,aliens;
	
	Random myRandom;
	char[] chars = {'q','p',' '};

	public mySurfaceView(Context context) {
		super(context);

		shipBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
		alien1Bmp = BitmapFactory.decodeResource(getResources(),  R.drawable.alien );
		alien2Bmp = BitmapFactory.decodeResource(getResources(),  R.drawable.alien2 );
		shotBmp = BitmapFactory.decodeResource(getResources(),  R.drawable.shot );
		bg = BitmapFactory.decodeResource(getResources(),  R.drawable.bg );
		left =0; right = 360;
		top =80; bottom =400;
		setup();
		
		red = new Paint();
		red.setColor(Color.BLUE);
		red.setStyle(Paint.Style.FILL); 
		black = new Paint();  //default is black
		black.setStyle(Paint.Style.STROKE); 
		myRandom = new Random();
		getHolder().addCallback(this);
		thread = new myThread(getHolder(), this);


	}
	public void setup() {
		//setup objects obj(int tx, int ty, int w, int wid, int hei)
		ship = new obj(((left+right)/2)-shipBmp.getWidth(),bottom-shipBmp.getHeight(), 0,shipBmp.getWidth(),shipBmp.getHeight());
		moveship = 0; tofire=false;
		aliens = new Vector<obj>();
		shots = new Vector<obj>();

	}
	@Override
	public void onDraw(Canvas c) {
		//System.out.println("w= "+ c.getWidth()+" h="+c.getHeight());
		c.drawColor(Color.BLACK);
		//draw background
		c.drawBitmap(bg, 0, 0, null);
		//draw score
		c.drawText("Score: "+score, 10, 10, black);
		//now add our stuff
		//draw the ship
		c.drawBitmap(shipBmp,ship.x, ship.y, null);
		//draw aliens
		obj temp;
		if (!aliens.isEmpty()) {
		  for(int i=0; i<aliens.size();i++) {
			  temp = aliens.elementAt(i);
			  temp.imgcnt();
			  if (temp.whichpic ==0)
			    c.drawBitmap(alien1Bmp,temp.x, temp.y, null);
			  else 
				c.drawBitmap(alien2Bmp,temp.x, temp.y, null);
		  }
		} 
			
		//draw shots
		if (!shots.isEmpty()) {
			  for(int i=0; i<shots.size();i++) {
				temp = shots.elementAt(i);	
				c.drawBitmap(shotBmp,temp.x, temp.y, null);
			  }
		}
		if (gameover) {
			c.drawText("GAME OVER", 100, 100,black);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// simply copied from sample application LunarLander:
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see net.rim.device.api.ui.Screen#touchEvent(net.rim.device.api.ui.TouchEvent)
	 */
	@Override public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		// Retrieve the new x and y touch positions
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (y>bottom) { //below playing area
				if (x<120) { //left
					moveship = -5;
					return true;
				} else if (x<240) { //fire
					tofire = true;
					return true;
				} else if (x<360) {
					moveship = 5;
					return true;
				}
			}
		}
		return false;
	}
    
	/*
	 * (non-Javadoc)
	 * @see android.view.View#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		char key = event.getMatch(chars);
		System.out.println("Key is "+key);
		if (key == 'q') {
			moveship = -5;
			return true;
		} else if (key=='p') {
			moveship = 5;
			return true;
		} else if (key==' ') {
			tofire = true;
			//System.out.println("Fire!");
			return true;
		}
		return false;
		
	}
	void checkGameState() {
		obj temp,tmp2;
		//move ship and deal with new shot.
		if (moveship !=0) { //going left maybe
			if (ship.x+moveship >=left  && ship.x+moveship<=right-shipBmp.getWidth())  { //don't move ship off edge of the board
				ship.move(moveship, 0);
			}
			moveship =0;
		}
		if (tofire) {
			if (shots.size() <maxShots) {
				shots.addElement(new obj(ship.x +(shipBmp.getWidth()/2)-(shotBmp.getWidth()/2),bottom-shipBmp.getHeight()-shotBmp.getHeight(),0,shotBmp.getWidth(), shotBmp.getHeight()));
			}
			tofire = false;
		}
		//aliens section
		if (! aliens.isEmpty()) {
			//move aliens and check for landing.
			for(int i=0; i<aliens.size(); i++) {
				temp = (obj)aliens.elementAt(i);
				if (temp.tickU(3)) { //yes move the alien
					//now direction 
					if (temp.tick2U(15)) {  //choose a new direction
						temp.dir = myRandom.nextInt(3) -1;
					}
					if (temp.x+ship.dir <=left  || temp.x+ship.dir>=right-alien1Bmp.getWidth())  { //don't move alien off edge of the board
						temp.dir *=-1;
					}
					if (temp.y +1 +alien1Bmp.getHeight()>= bottom) {  //alien landed.  game over!
						gameover = true;
					}
				   temp.move(temp.dir, +1); 
				}
			}
			//add another alien?
			if (aliens.size() <maxAliens) {
		      if (myRandom.nextInt(100)>97) { //3 percent change of a new alien being added right now.
	            int x = left + myRandom.nextInt(right - left - alien1Bmp.getWidth());
	            
	            aliens.addElement(new obj(x,top,0,alien1Bmp.getWidth(), alien1Bmp.getHeight()));
		      }
			}
		} else{  //no aliens on the board, so add a new one
            int x = left+ myRandom.nextInt(right - left - alien1Bmp.getWidth());
            aliens.addElement(new obj(x,top,0,alien1Bmp.getWidth(), alien1Bmp.getHeight()));
		}

		//move shots
		if (!shots.isEmpty()) {
			for(int i=0; i<shots.size(); i++) {
				temp = (obj)shots.elementAt(i);
				if (temp.y-1 > top) {
				   temp.move(0, -1); 
				} else {
					//remove shot
					temp.dead();
					shots.removeElementAt(i);
					//decrement index? to see next one?
					--i;
				}
			}
		}
		//check for collisions
		if (!(shots.isEmpty() || aliens.isEmpty())) { //no collisions if no aliens or no shots
			for(int i=0; i<shots.size(); i++) {
				temp = (obj)shots.elementAt(i);
				for(int j=0; j<aliens.size() && temp.alive;j++){
					tmp2 = (obj)aliens.elementAt(j);
					if (temp.collision(tmp2.rec)){
						temp.dead();
						tmp2.dead();
						score += tmp2.score();
						aliens.removeElementAt(j);
					}
					if (!temp.alive) {
						shots.removeElementAt(i);
						i--;
					}
				}
			}
		}
	}
	class myThread extends Thread {
		private SurfaceHolder _surfaceHolder;
		private mySurfaceView _mySurfaceView;
		private boolean _run = false;

		public myThread(SurfaceHolder surfaceHolder, mySurfaceView SurfaceView) {
			_surfaceHolder = surfaceHolder;
			_mySurfaceView = SurfaceView;
		}
		public void setRunning(boolean run) {
			_run = run;
		}
		@Override
		public void run() {
			Canvas c;
	        gameover = false;
			while (_run) {
				if (!gameover) {
					//verify game state, move objects, etc...
					checkGameState();
				}
				//redraw screen?
				c = null;
				try {
					c = _surfaceHolder.lockCanvas(null);
					synchronized (_surfaceHolder) {
						_mySurfaceView.onDraw(c);
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}


}
