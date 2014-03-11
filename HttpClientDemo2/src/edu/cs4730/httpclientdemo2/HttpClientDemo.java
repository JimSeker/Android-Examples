package edu.cs4730.httpclientdemo2;

import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

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
     setContentView(R.layout.main);
     output = (TextView) findViewById(R.id.output);
     output.append("\n");
     mkconn = (Button) findViewById(R.id.makeconn);
     mkconn.setOnClickListener(this);
 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		URI url1;
		try {
			url1 = new URI("http://www.cs.uwyo.edu/~seker/courses/4730/index.html");
			new doNetwork().execute(url1);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

 
/*
 * Shoulds how to use an AsyncTask with a HttpClient method.
 */
 class doNetwork  extends AsyncTask<URI, String, String> {
	 
	 /*
	  * while this could have been in the doInBackground, I reused the 
	  * method already created the thread class.
	  * 
	  * This downloads a text file and returns it to doInBackground.
	  */
	 public String executeHttpGet(URI url) throws Exception {
	     BufferedReader in = null;
	     try {
	     	HttpClient client = new DefaultHttpClient();
	     	HttpGet request = new HttpGet();
	     	request.setURI(url);
	     	publishProgress("Requesting web page.\n");
	     	HttpResponse response = client.execute(request);
	     	publishProgress("Webpage retreived, processing it.\n");
	     	in = new BufferedReader	(new InputStreamReader(response.getEntity().getContent()));
	     	StringBuffer sb = new StringBuffer("");
	     	String line = "";
	     	String NL = System.getProperty("line.separator");
	     	while ((line = in.readLine()) != null) {
	     		sb.append(line + NL);
	     	}
	     	in.close();
	     	String page = sb.toString();
	     	publishProgress("Processed page:");
	     	return page;
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
	@Override
	protected String doInBackground(URI... params) {
		String page = "";
		publishProgress("Attempting to retreive webpage ...\n");
		try {
			page = executeHttpGet(params[0]);
			publishProgress("Finished\n");
		} catch (Exception e) {
			publishProgress("Failed to retreive webpage ...\n");
			publishProgress(e.getMessage());

		}
		return page;  //return the page downloaded.
	}
	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 * 
	 * This takes the place of handlers and my makemsg method, since we can directly access the screen.
	 * 
	 */
    protected void onProgressUpdate(String... progress) {
    	output.append(progress[0]);
    }
    /*
     * So the file has been downloaded and in this simple example it is displayed
     * to the screen.
     * 
     * (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    protected void onPostExecute(String result) {
    	output.append(result);
    }


 }
 


}
