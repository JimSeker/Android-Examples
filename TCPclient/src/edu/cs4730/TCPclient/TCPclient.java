package edu.cs4730.TCPclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket; 


public class TCPclient extends Activity implements Button.OnClickListener{
    /** Called when the activity is first created. */
   TextView output;
   Button  mkconn;
   EditText hostname, port;
	Thread myNet;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        output = (TextView) findViewById(R.id.output);
        output.append("\n");
        hostname = (EditText) findViewById(R.id.EThostname);
        hostname.setText("10.0.2.2"); //This address is the localhost for the computer the emulator is running on.
        port = (EditText) findViewById(R.id.ETport);
        mkconn = (Button) findViewById(R.id.makeconn);
        mkconn.setOnClickListener(this);
    }
    

	@Override
	public void onClick(View v) {
		doNetwork stuff = new doNetwork();
		myNet = new Thread(stuff);
		myNet.start();
		//((doNetwork) myNet).out.println("hi");
		stuff.out.println("");

	}
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	output.append(msg.getData().getString("msg"));
        }

    };
    public void mkmsg(String str) {
		//handler junk, because thread can't update screen!
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putString("msg", str);
		msg.setData(b);
	    handler.sendMessage(msg);
    }
    class doNetwork  implements Runnable {
    	public PrintWriter out;
    	public  BufferedReader in;
    	 
    	public void run() {
    		 
        
        int p = Integer.parseInt(port.getText().toString());
        String h = hostname.getText().toString();
		mkmsg("host is "+h +"\n");
		mkmsg(" Port is " +p + "\n");
		try {
            InetAddress serverAddr = InetAddress.getByName(h);
            mkmsg("Attempt Connecting..." + h +"\n");
            Socket socket = new Socket(serverAddr, p);
            String message = "Hello from Client android emulator";

            //made connection, setup the read (in) and write (out)
            out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            
            //now send a message to the server and then read back the response.
                try {
                 //write a message to the server
                 mkmsg("Attempting to send message ...\n");                 
                 out.println(message);
                 mkmsg("Message sent...\n");
                 
                 //read back a message from the server.
                mkmsg("Attempting to receive a message ...\n"); 
                 String str = in.readLine();
                 mkmsg("received a message:\n" + str+"\n");

                 mkmsg("We are done, closing connection\n");
              } catch(Exception e) {
                  mkmsg("Error happened sending/receiving\n");

              } finally {
            	  in.close();
            	  out.close();
                  socket.close();
              }

          } catch (Exception e) {
      		mkmsg("Unable to connect...\n");
          } 
       }
    }
}