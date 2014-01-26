package com.example.hubbub_merchant;

import com.example.hubbub.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class FullscreenActivity extends Activity {
	
	public final static String EXTRA_USER_ID = "com.example.hubbub.USER_ID";
	public final static String EXTRA_METRIC_VALUE = "com.example.hubbub.METRIC_VALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fullscreen, menu);
        return true;
    }
    
    public void sendData(View view) {
    	Intent intent = new Intent(this, SendDataToHub.class);
    	EditText userIdView = (EditText) findViewById(R.id.user_id);
    	EditText metricView = (EditText) findViewById(R.id.metric); 
    	String userId = userIdView.getText().toString();
    	String metricValue = metricView.getText().toString(); // we'll convert this to a float in the SendDataToHub Activity
    	intent.putExtra(EXTRA_USER_ID, userId);
    	intent.putExtra(EXTRA_METRIC_VALUE, metricValue);
    	startActivity(intent);
    }
    
}
