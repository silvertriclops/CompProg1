package me.silvertriclops.buttonsample;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends ActionBarActivity {

	private String mURL = "http://developer.android.com";
	private String mURL2 = "http://silvertriclops.me";
	private String mURL3 = "https://twitter.com/silvertriclops";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void openWebsite(View view) {
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mURL));
    	startActivity(browserIntent);
    }
    
    public void openWebsite2(View view) {
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mURL2));
    	startActivity(browserIntent);
    }
    
    public void openWebsite3(View view) {
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mURL3));
    	startActivity(browserIntent);
    }

}
