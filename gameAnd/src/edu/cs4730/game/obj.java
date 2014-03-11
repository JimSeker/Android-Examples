package edu.cs4730.game;

import android.graphics.Rect;


/*
 * obj.java
 *
 * © <your company here>, 2003-2008
 * Confidential and proprietary.
 */

class obj {
    public int x, y;
    public boolean alive;
    public int whichpic;
    public Rect rec;
    public int imgtick, tick, tick2, dir;  //mostly for moving aliens...
    obj() {  x=0; y=0; alive=false; tick=0; tick2=0; dir=0;imgtick=0;}
    obj(int tx, int ty, int w, int wid, int hei) {
      x =tx; y = ty; whichpic=w;
      rec = new Rect(x,y,x+wid,y+hei);
      alive = true;
      tick=0; tick2=0; imgtick=0;
      dir =0;
    }
    public void imgcnt() {
    	imgtick++;
    	if (imgtick >3) {
    		imgtick =0;
    		if (whichpic==0) 
    		  whichpic=1;
    		else
    		  whichpic=0;
    		
    	}
    }
    public void move(int tx, int ty) {
      x+=tx; y+=ty;
      rec.offsetTo(x,y);
    }
    public void set(int tx, int ty) {
      x=tx; y=ty;
      rec.offsetTo(x,y);
    }
    public boolean tickU(int tickMax) {
    	tick++;
    	if (tick > tickMax) {
    		tick =0;
        	return true;
    	}
    	return false;
    }
    public boolean tick2U(int tickMax) {
    	tick2++;
    	if (tick2 > tickMax) {
    		tick2 =0;
        	return true;
    	}
    	return false;
    }
    public void dead() {
      alive = false;
    }
 
    public boolean collision(Rect them) {
      return rec.intersects(them.left,them.top, them.right,them.bottom);
    }
    public int score() {
      return 100;
    }
} 
