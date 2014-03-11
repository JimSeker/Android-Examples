package edu.cs4730.webView;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class webViewDemo extends Activity {
	WebView browser;
	Button btnZoomIn, btnZoomOut, btnBack, btnForward;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        browser=(WebView)findViewById(R.id.webkit);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.loadUrl("http://www.cs.uwyo.edu");
        //browser.loadUrl("");
        browser.setWebViewClient(new CallBack());
        btnZoomIn=(Button)findViewById(R.id.btnZoomIn);
        btnZoomIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				browser.zoomIn();
			}
        });
        btnZoomOut=(Button)findViewById(R.id.btnZoomOut);
        btnZoomOut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				browser.zoomOut();
			}
        });
        btnBack=(Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (browser.canGoBack()) {
					browser.goBack();
				}
			}
        });
        btnForward=(Button)findViewById(R.id.btnFoward);
        btnForward.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (browser.canGoForward()) {
					browser.goForward();
				}
			}
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private class CallBack extends WebViewClient {
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		browser.loadUrl(url);
    		return true;
    	}
    }
}