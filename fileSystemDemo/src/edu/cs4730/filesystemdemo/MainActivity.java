package edu.cs4730.filesystemdemo;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	String TAG = "MainActivity";
	ViewPager viewPager;
	frag_localp one;
	frag_localpub two;
	frag_ext three;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		one = new frag_localp();
		two = new frag_localpub();
		three = new frag_ext();
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		viewPager.setAdapter(new myFragmentPagerAdapter(fragmentManager));

	}


	public class myFragmentPagerAdapter extends FragmentPagerAdapter {
		int PAGE_COUNT =3;
		
		public myFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			switch (position) {
			  case 0: return one;
			  case 1: return two;
			  case 2: return three;
			  default: return null;
			}
		}

		@Override
		public int getCount() {

			return PAGE_COUNT;
		}
	
		//getPageTitle required for the PageStripe to work and have a value.
        @Override
        public CharSequence getPageTitle(int position) {
		  switch (position) {      	
		    case 0: return "Local file example";
		    case 1: return "Local file public";
		    case 2: return "External files";
		    default: return "Page "+ String.valueOf(position +1);
		  }   	
        }
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	


}
