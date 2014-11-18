package me.silvertriclops.buttonsample;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends ActionBarActivity {

	private String mURL = "http://developer.android.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void openWebsite(View view) {
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mURL));
    	startActivity(browserIntent);
    }

}
