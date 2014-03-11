package edu.cs4730.filesystemdemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;



public class frag_ext extends Fragment {
	TextView logger;
	String TAG = "ext";
	MainActivity parent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "OnCreateView");
		View view = inflater.inflate(R.layout.frag_ext, container, false);
		logger = (TextView) view.findViewById(R.id.loggerext);
		parent =  (MainActivity) getActivity();
		
		view.findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				logger.setText("Output:\n");
				extfolder();
			}
		});
		
		return view;
	}

	public void extfolder() {
    	logger.append("\nOn to external storage\n");
    	boolean mExternalStorageAvailable = false;
    	boolean mExternalStorageWriteable = false;
    	String state = Environment.getExternalStorageState();

    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    	    // We can read and write the media
    	    mExternalStorageAvailable = mExternalStorageWriteable = true;
    	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
    	    // We can only read the media
    	    mExternalStorageAvailable = true;
    	    mExternalStorageWriteable = false;
    	} else {
    	    // Something else is wrong. It may be one of many other states, but all we need
    	    //  to know is we can neither read nor write
    	    mExternalStorageAvailable = mExternalStorageWriteable = false;
    	}
    	if (mExternalStorageAvailable) {
    		logger.append("Media is available and ");
    		if (mExternalStorageWriteable)
    			logger.append("writable\n");
    		else
    			logger.append("Read only\n");
    		
    	} else {
    		logger.append("Media is not available\n");
    	}
    	if (mExternalStorageWriteable) {
    		File extdir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    		extdir.mkdir(); //just in case it doesn't exist yet.
    		File file = new File(extdir,"Myfiletest.txt");
    		try {
                BufferedWriter bW = new BufferedWriter(new FileWriter(file,true));
                bW.write("Hi There");
                bW.newLine();
                bW.flush();
                bW.close();
    			/*
				FileOutputStream f = new FileOutputStream(file, true);  //append!  false to overwrite.
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(f));
				out.writeUTF("Hi There\n");
	    		out.close();
	    		*/
	    		logger.append("Wrote a line to the file myfiletest in downloads");
			} catch (FileNotFoundException e) {
				Log.d(TAG, "Can create file: "+ e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "write failure: "+ e.getMessage());
			}
    	}
    	logger.append("\nReading back data:\n");
    	if (mExternalStorageAvailable) {
    		File extdir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    		extdir.mkdir(); //just in case it doesn't exist yet.
    		File file = new File(extdir,"Myfiletest.txt");
    		String line = "";
    		try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                line = in.readLine();
                while (line != null) {
                	logger.append(line+"\n");
                	line = in.readLine();
                }
                in.close();
                in = null;
    			/*
				FileInputStream f = new FileInputStream(file);
				DataInputStream in = new DataInputStream(new BufferedInputStream(f));
				while(true)
					try {
						logger.append(in.readUTF() + "\n");
					} catch (EOFException e) {  //reach end of file
						in.close();
					}
					*/
			} catch (FileNotFoundException e) {
				Log.d(TAG, "file not found: "+ e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "IO error: "+ e.getMessage());
			}
    	}
    	
	}
}
