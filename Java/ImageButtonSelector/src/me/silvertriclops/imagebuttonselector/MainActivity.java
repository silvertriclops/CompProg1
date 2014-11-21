package me.silvertriclops.imagebuttonselector;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void showNotification(View view) {
    	Context context = getApplicationContext();
    	CharSequence text = "You clicked the image! MOOOOO!!!1!11!";
    	int duration = Toast.LENGTH_SHORT;
    	
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    
    public void showNotification2(View view) {
    	Context context = getApplicationContext();
    	CharSequence text = "You clicked the second image! squeeeak!!1!";
    	int duration = Toast.LENGTH_SHORT;
    	
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
}
