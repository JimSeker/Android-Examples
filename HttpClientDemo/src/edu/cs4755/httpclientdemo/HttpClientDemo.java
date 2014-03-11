package edu.cs4755.httpclientdemo;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientDemo extends Activity implements Button.OnClickListener{
	   TextView output;
	   Button  mkconn;
	   
 /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
//      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
//      StrictMode.setThreadPolicy(policy);
     
     setContentView(R.layout.main);
     output = (TextView) findViewById(R.id.output);
     output.append("\n");
     mkconn = (Button) findViewById(R.id.makeconn);
     mkconn.setOnClickListener(this);
 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
 
 
 public void executeHttpGet() throws Exception {
     BufferedReader in = null;
     try {
     	HttpClient client = new DefaultHttpClient();
     	HttpGet request = new HttpGet();
     	request.setURI(new URI("http://www.cs.uwyo.edu/~seker/courses/4730/index.html"));
     	mkmsg("Requesting web page.\n");
     	HttpResponse response = client.execute(request);
     	mkmsg("Webpage retreived, processing it.\n");
     	in = new BufferedReader	(new InputStreamReader(response.getEntity().getContent()));
     	StringBuffer sb = new StringBuffer("");
     	String line = "";
     	String NL = System.getProperty("line.separator");
     	while ((line = in.readLine()) != null) {
     		sb.append(line + NL);
     	}
     	in.close();
     	String page = sb.toString();
     	mkmsg("Processed page:");
     	mkmsg(page);
     } finally {
     	if (in != null) {
     		try {
     			in.close();
     		} catch (IOException e) {
     			e.printStackTrace();
     		}
     	}
     	
     }
 }
 class doNetwork  implements Runnable {
 	public void run() {
 		 mkmsg("Attempting to retreive webpage ...\n");
 		try {
				executeHttpGet();
				mkmsg("Finished\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				mkmsg("Failed to retreive webpage ...\n");
				mkmsg(e.getMessage());
				e.printStackTrace();
			}
 	}
 }
 


}
