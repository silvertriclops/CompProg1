package me.silvertriclops.simpletoast;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Context context = getApplicationContext();
		CharSequence text = context.getString(R.string.toast_notification);
		int duration = Toast.LENGTH_SHORT;
		
		final Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
		
		final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	toast.show();
            }
        });
	}
}