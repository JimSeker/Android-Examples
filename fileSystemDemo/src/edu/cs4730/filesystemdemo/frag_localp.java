package edu.cs4730.filesystemdemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;



public class frag_localp extends Fragment {
	TextView logger;
	String TAG = "localp";
	MainActivity parent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "OnCreateView");
		View view = inflater.inflate(R.layout.frag_localp, container, false);
		logger = (TextView) view.findViewById(R.id.loggerlp);
		
		view.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				logger.setText("Output:\n");
				readwritelocal() ;
			}
		});
		
		return view;
	}
	
	public void readwritelocal() {
    	logger.append("check for local files\n");
    	String flist[] = getActivity().fileList();
		DataInputStream in;
		DataOutputStream dos;
    	if (flist.length>0) {
    		for(int i=0; i<flist.length; ++i) {
    			logger.append(flist[i] +"\n");

    			try {
					in = new DataInputStream( getActivity().openFileInput(flist[i]) ) ;
					while(true)
						try {
							logger.append(in.readUTF() + "\n");
						} catch (EOFException e) {  //reach end of file
							in.close();
						}
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}
				logger.append("Now appending to it");
				try {
					dos = new DataOutputStream( getActivity().openFileOutput(flist[i], Context.MODE_APPEND));
		    		dos.writeUTF("Another line");
		    		dos.close();
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}
    		}
    	} else {
    		logger.append("No current files storaged internally. Creating one\n");

				try {
					dos = new DataOutputStream( getActivity().openFileOutput("FileExample", Context.MODE_PRIVATE));
		    		dos.writeUTF("First line of the file");
		    		dos.close();
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}


    	}
	}
}
