package edu.cs4730.TCPserv;

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
import java.net.ServerSocket;
import java.net.Socket; 


public class TCPserv extends Activity implements Button.OnClickListener{
	
	   TextView output;
	   Button  mkconn;
	   EditText port;
	   
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        output = (TextView) findViewById(R.id.output);
        output.append("\n\n");
        port = (EditText) findViewById(R.id.ETport);
        mkconn = (Button) findViewById(R.id.makeconn);
        mkconn.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		new Thread(new doNetwork()).start();

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
    	public void run() {

        
        int p = Integer.parseInt(port.getText().toString());
		mkmsg(" Port is " +p + "\n");
		
        try {
        	 mkmsg("Waiting on Connecting...\n");
            System.out.println("S: Connecting...");
              ServerSocket serverSocket = new ServerSocket(p);
              while (true) {         
                 Socket client = serverSocket.accept();
                 System.out.println("S: Receiving...");

                 try {

                     mkmsg("Attempting to receive a message ...\n"); 
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
                     String str = in.readLine();
                     mkmsg("received a message:\n" + str+"\n");

         			String message = "Hello from server android emulator";

                     PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(client.getOutputStream())),true);
                     mkmsg("Attempting to send message ...\n");                 
                     out.println(message);
                     mkmsg("Message sent...\n");
                     
                     
                 } catch(Exception e) {
                     mkmsg("Error happened sending/receiving\n");
                    
                 } finally {
                	 mkmsg("We are done, closing connection\n");
                          client.close();
                     }
              }      
          } catch (Exception e) {
        	  mkmsg("Unable to connect...\n");
          } 

       }
    }
}