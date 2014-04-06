package com.example.hubbub_merchant;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hubbub.R;

public class FullscreenActivity extends Activity {
	
	public final static String EXTRA_USER_ID = "com.example.hubbub.USER_ID";
	public final static String EXTRA_METRIC_VALUE = "com.example.hubbub.METRIC_VALUE";
	
	public static Map<String, Model> clientMap = new HashMap<String, Model>();
	public static Map<String, Model> merchantMap = new HashMap<String, Model>();
	
	private final static int INTERVAL = 1000 * 10; // 10 seconds
	Handler mHandler;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
 
    
        getInformation(getWindow().getDecorView().findViewById(android.R.id.content));
	}
	
	
	public void getInformation(View view) {
		new GetModels(this, (Spinner)findViewById(R.id.spinner_client), (Spinner)findViewById(R.id.spinner_merchant), false).execute("getClients");
        
		if (null != view.getTag() && view.getTag().equals("called")) {
			new GetModels(this, (Spinner)findViewById(R.id.spinner_client), (Spinner)findViewById(R.id.spinner_merchant), true).execute("getMerchants");
		}
		else {
			new GetModels(this, (Spinner)findViewById(R.id.spinner_client), (Spinner)findViewById(R.id.spinner_merchant), false).execute("getMerchants");
		}
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fullscreen, menu);
        return true;
    }
    
    
    public void sendData(View view) {
    	TextView displaySending = (TextView)findViewById(R.id.display_sending);
    	displaySending.setText("Sending...");
    	
    	Spinner clientSpinner = (Spinner)findViewById(R.id.spinner_client);
    	Spinner merchantSpinner = (Spinner)findViewById(R.id.spinner_merchant);
    	EditText metricView = (EditText) findViewById(R.id.metric);
    	
    	String clientName = clientSpinner.getSelectedItem().toString();
    	String merchantName = merchantSpinner.getSelectedItem().toString();
    	String metricValue = metricView.getText().toString();

    	new SendInteraction(this, displaySending, metricView, clientName, clientMap.get(clientName).id, merchantName, merchantMap.get(merchantName).id, merchantMap.get(merchantName).key, metricValue).execute();
    }
    
}
